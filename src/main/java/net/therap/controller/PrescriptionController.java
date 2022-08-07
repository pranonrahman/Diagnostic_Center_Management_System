package net.therap.controller;

import net.therap.editor.FacilityEditor;
import net.therap.model.Facility;
import net.therap.model.Prescription;
import net.therap.service.FacilityService;
import net.therap.service.PatientService;
import net.therap.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

    private static final String VIEW_PAGE = "prescription/form";

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private FacilityEditor facilityEditor;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @InitBinder
    private void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Facility.class, facilityEditor);
    }

    @GetMapping("/create")
    public String loadViewPage(ModelMap modelMap) {
        modelMap.put("prescription", new Prescription());
        modelMap.put("facilities", facilityService.findAll());

        return VIEW_PAGE;
    }

    @PostMapping("/create")
    public String processCreate(@ModelAttribute("prescription") Prescription prescription, ModelMap modelMap) {
        modelMap.put("prescription", new Prescription());
        modelMap.put("facilities", facilityService.findAll());

        return VIEW_PAGE;
    }

    @GetMapping("/view")
    public String loadViewPage(@RequestParam("id") String id, ModelMap modelMap){
        modelMap.put("readonly", true);
        modelMap.put("facilities", facilityService.findAll());
        modelMap.put("prescription", prescriptionService.findById(Long.parseLong(id)));

        return VIEW_PAGE;
    }
}
