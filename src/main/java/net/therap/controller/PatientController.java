package net.therap.controller;

import net.therap.model.Patient;
import net.therap.model.Prescription;
import net.therap.service.PatientService;
import net.therap.viewModel.PrescriptionViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author amimul.ehsan
 * @since 03/08/2022
 */
@Controller
@RequestMapping("/patient")
public class PatientController {

    private static final String VIEW_PAGE = "patient/list";

    private static final String HISTORY_PAGE = "patient/history";

    @Autowired
    private PatientService patientService;

    @GetMapping("/list")
    public String loadPatientListPage(ModelMap modelMap) {
        modelMap.put("patients", patientService.findAll());

        return VIEW_PAGE;
    }

    @GetMapping("/history")
    public String loadList(@RequestParam("id") String id, ModelMap modelMap) {
        Patient patient = patientService.findById(Long.valueOf(id));

        List<PrescriptionViewModel> prescriptionViewModels = new ArrayList<>();
        Set<Prescription> prescriptions = patient.getPrescriptions();

        for(Prescription prescription: prescriptions) {
            prescriptionViewModels.add(new PrescriptionViewModel(prescription));
        }

        Collections.sort(prescriptionViewModels);

        modelMap.put("patientName", patient.getPerson().getName());
        modelMap.put("prescriptionViewModels", prescriptionViewModels);

        return HISTORY_PAGE;
    }
}
