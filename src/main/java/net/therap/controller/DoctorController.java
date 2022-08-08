package net.therap.controller;

import net.therap.model.*;
import net.therap.service.DoctorService;
import net.therap.service.PatientService;
import net.therap.viewModel.PatientViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author amimul.ehsan
 * @since 07/08/2022
 */
@Controller
@RequestMapping("/doctor")
@SessionAttributes({"user", "role"})
public class DoctorController {

    private static final String PATIENTS_VIEW_PAGE = "patient/list";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("")
    public String loadPatientListPage(@ModelAttribute("role") Role role, @ModelAttribute("user") Person user, ModelMap modelMap) {
        long doctorId = user.getDoctor().getId();
        Set<Prescription> prescriptions = doctorService.findById(doctorId).getPrescriptions();
        List<PatientViewModel> patients = new ArrayList<>();

        for(Prescription prescription : prescriptions) {
            patients.add(new PatientViewModel(prescription.getPatient()));
        }

        modelMap.put("doctorId", doctorId);
        modelMap.put("patients", patients);

        return PATIENTS_VIEW_PAGE;
    }
}
