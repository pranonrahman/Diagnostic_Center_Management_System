package net.therap.controller;

import net.therap.model.Doctor;
import net.therap.model.Patient;
import net.therap.model.Prescription;
import net.therap.service.DoctorService;
import net.therap.service.PatientService;
import net.therap.viewModel.PrescriptionViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @author amimul.ehsan
 * @since 03/08/2022
 */
@Controller
@RequestMapping("/patient")
public class PatientController {

    private static final String HISTORY_PAGE = "patient/history";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/history")
    public String loadList(@RequestParam("id") String id, ModelMap modelMap) {
        String doctorId = "11";
        Doctor doctor = doctorService.findById(Long.parseLong(doctorId));
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

        modelMap.put("patientName", patient.getPerson().getName());
        modelMap.put("prescriptionViewModels", allPrescriptionViewModels);
        modelMap.put("doctorSpecificPrescriptions", doctorSpecificPrescriptions);

        return HISTORY_PAGE;
    }
}
