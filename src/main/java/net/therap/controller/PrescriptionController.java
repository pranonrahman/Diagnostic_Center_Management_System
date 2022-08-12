package net.therap.controller;

import net.therap.editor.FacilityEditor;
import net.therap.entity.*;
import net.therap.service.DoctorService;
import net.therap.service.FacilityService;
import net.therap.service.PatientService;
import net.therap.service.PrescriptionService;
import net.therap.util.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
@SessionAttributes("user")
@RequestMapping("/prescription")
public class PrescriptionController {

    private static final String VIEW_PAGE = "prescription/form";

    private static final String PRESCRIPTION_LIST_VIEW_PAGE = "prescription/list";

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private FacilityEditor facilityEditor;

    @InitBinder
    private void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Facility.class, facilityEditor);
    }

    @GetMapping("/view")
    public String loadViewPage(@ModelAttribute("user") User user,
                               @RequestParam("id") String id,
                               ModelMap model) {

        model.put("doctorId", RoleUtil.userContains(user, RoleEnum.DOCTOR) ? user.getDoctor().getId() : 0);
        setupReferenceData(Long.parseLong(id), model);

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String loadPrescriptionList(@ModelAttribute("user") User user, ModelMap model) {
        Patient patient = user.getPatient();
        List<Prescription> prescriptions = patient.getPrescriptions();

        Collections.sort(prescriptions);

        model.put("patientName", user.getName());
        model.put("prescriptions", prescriptions);

        return PRESCRIPTION_LIST_VIEW_PAGE;
    }

    @GetMapping("/save")
    public String loadEditPage(@RequestParam("id") String id, ModelMap model) {
        model.put("action", "edit");
        setupReferenceData(Long.parseLong(id), model);

        return VIEW_PAGE;
    }

    @PostMapping("/save")
    public String processEdit(@ModelAttribute("prescription") Prescription prescription, ModelMap model) {
        prescription.setPatient(patientService.findById(prescription.getPatient().getId()));
        prescription.setDoctor(doctorService.findById(prescription.getDoctor().getId()));
        prescription.setDateOfVisit(new Date());

        prescriptionService.saveOrUpdate(prescription);
        setupReferenceData(0, model);

        return "redirect:/prescription/view?id=" + prescription.getId();
    }

    private void setupReferenceData(long prescriptionId, ModelMap model) {
        model.put("facilities", facilityService.findAll());

        if (prescriptionId == 0) {
            model.put("prescription", new Prescription());
        } else {
            model.put("prescription", prescriptionService.findById(prescriptionId));
        }
    }
}
