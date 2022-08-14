package net.therap.controller;

import net.therap.editor.FacilityEditor;
import net.therap.entity.Facility;
import net.therap.entity.Patient;
import net.therap.entity.Prescription;
import net.therap.entity.User;
import net.therap.exception.InsufficientAccessException;
import net.therap.exception.RecordNotFoundException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static net.therap.controller.PatientController.USER_CMD;
import static net.therap.entity.RoleEnum.DOCTOR;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
@SessionAttributes(USER_CMD)
@RequestMapping("/prescription")
public class PrescriptionController {

    private static final String VIEW_PAGE = "prescription/form";

    private static final String LIST_VIEW_PAGE = "prescription/list";

    private static final String USER_CMD = "user";

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

    @GetMapping
    public String loadViewPage(@ModelAttribute(USER_CMD) User user,
                               @RequestParam("id") String id,
                               ModelMap model) {

        Prescription prescription = prescriptionService.findById(Long.parseLong(id));

        setupReferenceData(prescription, model);
        model.put("doctorId", RoleUtil.userContains(user, DOCTOR) ? user.getDoctor().getId() : 0);

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String loadPrescriptionList(@ModelAttribute(USER_CMD) User user, ModelMap model) {
        Patient patient = user.getPatient();
        List<Prescription> prescriptions = patient.getPrescriptions();

        Collections.sort(prescriptions);

        model.put("prescriptions", prescriptions);

        return LIST_VIEW_PAGE;
    }

    @PostMapping
    public String processEdit(@ModelAttribute("prescription") Prescription prescription,
                              RedirectAttributes attributes) {

        prescription.setPatient(patientService.findById(prescription.getPatient().getId()));
        prescription.setDoctor(doctorService.findById(prescription.getDoctor().getId()));

        prescriptionService.saveOrUpdate(prescription);

        attributes.addAttribute("id", prescription.getId());
        attributes.addAttribute("success", true);

        return "redirect:/prescription";
    }

    private void setupReferenceData(Prescription prescription, ModelMap model) {
        model.put("facilities", facilityService.findAll());

        if (isNull(prescription)) {
            model.put("prescription", new Prescription());
        } else {
            model.put("prescription", prescription);
        }
    }
}
