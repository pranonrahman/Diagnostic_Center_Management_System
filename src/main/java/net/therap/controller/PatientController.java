package net.therap.controller;

import net.therap.model.*;
import net.therap.service.DoctorService;
import net.therap.service.PatientService;
import net.therap.viewModel.PatientViewModel;
import net.therap.viewModel.PrescriptionViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author amimul.ehsan
 * @since 03/08/2022
 */
@Controller
@SessionAttributes({"user", "role"})
@RequestMapping("/patient")
public class PatientController {

    private static final String HISTORY_PAGE = "patient/history";
    
    private static final String PATIENT_LIST_VIEW_PAGE = "patient/list";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/list")
    public String loadPatientListPage(@ModelAttribute("role") Role role, @ModelAttribute("user") User user, ModelMap model) {
        long doctorId = user.getDoctor().getId();
        Set<Prescription> prescriptions = doctorService.findById(doctorId).getPrescriptions();
        List<PatientViewModel> patients = new ArrayList<>();

        for(Prescription prescription : prescriptions) {
            patients.add(new PatientViewModel(prescription.getPatient()));
        }

        model.put("doctorId", doctorId);
        model.put("patients", patients);

        return PATIENT_LIST_VIEW_PAGE;
    }

    @GetMapping("/history")
    public String loadList(@ModelAttribute("user") User user, @RequestParam("id") String id, ModelMap model) {
        long doctorId = user.getDoctor().getId();
        Doctor doctor = doctorService.findById(doctorId);
        Patient patient = patientService.findById(Long.parseLong(id));

        List<PrescriptionViewModel> allPrescriptionViewModels = new ArrayList<>();
        List<PrescriptionViewModel> doctorSpecificPrescriptions = new ArrayList<>();
        Set<Prescription> allPrescriptions = patient.getPrescriptions();

        for (Prescription prescription : allPrescriptions) {
            if (prescription.getDoctor().equals(doctor)) {
                doctorSpecificPrescriptions.add(new PrescriptionViewModel(prescription));
            } else {
                allPrescriptionViewModels.add(new PrescriptionViewModel(prescription));
            }
        }

        Collections.sort(allPrescriptionViewModels);
        Collections.sort(doctorSpecificPrescriptions);

        model.put("patientName", patient.getUser().getName());
        model.put("prescriptionViewModels", allPrescriptionViewModels);
        model.put("doctorSpecificPrescriptions", doctorSpecificPrescriptions);

        return HISTORY_PAGE;
    }
}
