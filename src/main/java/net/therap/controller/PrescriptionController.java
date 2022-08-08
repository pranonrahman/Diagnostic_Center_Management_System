package net.therap.controller;

import net.therap.editor.FacilityEditor;
import net.therap.model.Facility;
import net.therap.model.Person;
import net.therap.model.Prescription;
import net.therap.service.DoctorService;
import net.therap.service.FacilityService;
import net.therap.service.PatientService;
import net.therap.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static java.util.Objects.nonNull;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
@SessionAttributes("user")
@RequestMapping("/prescription")
public class PrescriptionController {

    private static final String VIEW_PAGE = "prescription/form";

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
    public String loadViewPage(@ModelAttribute("user") Person user, @RequestParam("id") String id, ModelMap modelMap) {
        modelMap.put("action", "view");
        modelMap.put("facilities", facilityService.findAll());
        modelMap.put("doctorId", nonNull(user.getDoctor()) ? user.getDoctor().getId() : 0);
        modelMap.put("prescription", prescriptionService.findById(Long.parseLong(id)));

        return VIEW_PAGE;
    }

    @GetMapping("/edit")
    public String loadEditPage(@RequestParam("id") String id, ModelMap modelMap) {
        modelMap.put("action", "edit");
        modelMap.put("facilities", facilityService.findAll());
        modelMap.put("prescription", prescriptionService.findById(Long.parseLong(id)));

        return VIEW_PAGE;
    }

    @PostMapping("/edit")
    public String processEdit(@ModelAttribute("prescription") Prescription prescription, ModelMap modelMap) {
        prescription.setPatient(patientService.findById(prescription.getPatient().getId()));
        prescription.setDoctor(doctorService.findById(prescription.getDoctor().getId()));
        prescription.setDateOfVisit(new Date());

        prescriptionService.saveOrUpdate(prescription);
        modelMap.put("prescription", new Prescription());
        modelMap.put("facilities", facilityService.findAll());

        return "redirect:/prescription/view?id=" + prescription.getId();
    }
}
