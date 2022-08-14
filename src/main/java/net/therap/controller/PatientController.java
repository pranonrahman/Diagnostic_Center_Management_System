package net.therap.controller;

import net.therap.entity.Doctor;
import net.therap.entity.Patient;
import net.therap.entity.Prescription;
import net.therap.entity.User;
import net.therap.service.DoctorService;
import net.therap.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static net.therap.controller.PatientController.USER_CMD;

/**
 * @author amimul.ehsan
 * @since 03/08/2022
 */
@Controller
@SessionAttributes(USER_CMD)
@RequestMapping("/patient")
public class PatientController {

    private static final String HISTORY_PAGE = "patient/history";
    private static final String LIST_VIEW_PAGE = "patient/list";
    public static final String USER_CMD = "user";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/list")
    public String showList(@ModelAttribute(USER_CMD) User user,
                           ModelMap model) {

        long doctorId = user.getDoctor().getId();
        List<Prescription> prescriptions = doctorService.findById(doctorId).getPrescriptions();
        Set<Patient> patients = new HashSet<>();

        for(Prescription prescription : prescriptions) {
            patients.add(prescription.getPatient());
        }

        model.put("doctorId", doctorId);
        model.put("patients", patients);

        return LIST_VIEW_PAGE;
    }

    @GetMapping("/history")
    public String loadList(@ModelAttribute(USER_CMD) User user,
                           @RequestParam("id") String id,
                           ModelMap model) {

        long doctorId = user.getDoctor().getId();
        Doctor doctor = doctorService.findById(doctorId);
        Patient patient = patientService.findById(Long.parseLong(id));

        List<Prescription> otherPrescriptions = new ArrayList<>();
        List<Prescription> doctorSpecificPrescriptions = new ArrayList<>();
        List<Prescription> allPrescriptions = patient.getPrescriptions();

        for (Prescription prescription : allPrescriptions) {
            if (prescription.getDoctor().equals(doctor)) {
                doctorSpecificPrescriptions.add(prescription);
            } else {
                otherPrescriptions.add(prescription);
            }
        }

        Collections.sort(otherPrescriptions);
        Collections.sort(doctorSpecificPrescriptions);

        model.put("patientName", patient.getUser().getName());
        model.put("otherPrescriptions", otherPrescriptions);
        model.put("doctorSpecificPrescriptions", doctorSpecificPrescriptions);

        return HISTORY_PAGE;
    }
}
