package net.therap.controller;

import net.therap.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author amimul.ehsan
 * @since 03/08/2022
 */
@Controller
public class PatientController {

    private static final String VIEW_PAGE = "patient/list";

    @Autowired
    private PatientService patientService;

    @GetMapping("/patient/list")
    public String loadPatientListPage(ModelMap modelMap) {
        modelMap.put("patients", patientService.findAll());

        return VIEW_PAGE;
    }
}
