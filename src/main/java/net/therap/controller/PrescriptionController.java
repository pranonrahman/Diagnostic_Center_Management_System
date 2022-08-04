package net.therap.controller;

import net.therap.model.Facility;
import net.therap.model.Prescription;
import net.therap.property_editor.FacilityEditor;
import net.therap.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
public class PrescriptionController {

    private static final String VIEW_PAGE = "prescription/view";

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private FacilityEditor facilityEditor;

    @InitBinder
    private void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Facility.class, facilityEditor);
    }

    @GetMapping("/")
    public String loadViewPage(ModelMap modelMap) {
        modelMap.put("prescription", new Prescription());
        modelMap.put("facilities", facilityService.findAll());

        return VIEW_PAGE;
    }

    @PostMapping("/")
    public String processCreate(@ModelAttribute("prescription") Prescription prescription, ModelMap modelMap) {
        modelMap.put("prescription", new Prescription());
        modelMap.put("facilities", facilityService.findAll());

        return VIEW_PAGE;
    }
}
