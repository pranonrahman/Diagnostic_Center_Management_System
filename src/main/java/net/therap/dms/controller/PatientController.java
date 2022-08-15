package net.therap.dms.controller;

import net.therap.dms.entity.Patient;
import net.therap.dms.entity.Prescription;
import net.therap.dms.entity.User;
import net.therap.dms.helper.PatientHelper;
import net.therap.dms.service.DoctorService;
import net.therap.dms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static net.therap.dms.controller.PatientController.USER_CMD;

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

    @Autowired
    private PatientHelper patientHelper;

    @InitBinder
    private void InitBinder(WebDataBinder webDataBinder){
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/list")
    public String showList(ModelMap model) {

        User user = (User) model.getAttribute(USER_CMD);

        Set<Patient> patients = patientHelper.getPatientsForDoctor(user.getDoctor());

        setupReferenceDataForList(patients, model);

        return LIST_VIEW_PAGE;
    }

    @GetMapping("/history")
    public String showHistory(@RequestParam(value = "id", defaultValue = "0") long id,
                              ModelMap model) {

        Patient patient = patientService.findById(id);

        setupReferenceDataForHistory(patient, model);

        return HISTORY_PAGE;
    }

    private void setupReferenceDataForList(Set<Patient> patients, ModelMap model) {
        model.put("patients", patients);
    }

    private void setupReferenceDataForHistory(Patient patient,
                                              ModelMap model) {
        User user = (User) model.getAttribute(USER_CMD);
        List<Prescription> doctorSpecificPrescriptions = patientHelper.getPrescriptions(patient, user.getDoctor(), true);
        List<Prescription> otherPrescriptions = patientHelper.getPrescriptions(patient, user.getDoctor(), false);

        model.put("patient", patient);
        model.put("otherPrescriptions", otherPrescriptions);
        model.put("doctorSpecificPrescriptions", doctorSpecificPrescriptions);
    }
}
