package net.therap.controller;

import net.therap.model.Patient;
import net.therap.model.Prescription;
import net.therap.service.DoctorService;
import net.therap.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author amimul.ehsan
 * @since 07/08/2022
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private static final String PATIENTS_VIEW_PAGE = "patient/list";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/patients")
    public String loadPatientListPage(ModelMap modelMap) {
        String doctorId = "11";
        Set<Prescription> prescriptions = doctorService.findById(Long.parseLong(doctorId)).getPrescriptions();
        List<Patient> patients = new ArrayList<>();

        for(Prescription prescription : prescriptions) {
            patients.add(prescription.getPatient());
        }

        modelMap.put("doctorId", doctorId);
        modelMap.put("patients", patients);

        return PATIENTS_VIEW_PAGE;
    }
}
