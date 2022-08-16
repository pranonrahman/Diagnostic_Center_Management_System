package net.therap.dms.controller;

import net.therap.dms.entity.Patient;
import net.therap.dms.entity.Prescription;
import net.therap.dms.entity.User;
import net.therap.dms.helper.PatientHelper;
import net.therap.dms.service.AccessManager;
import net.therap.dms.service.DoctorService;
import net.therap.dms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

import static net.therap.dms.util.SessionUtil.getUser;

/**
 * @author amimul.ehsan
 * @since 03/08/2022
 */
@Controller
@RequestMapping("/patient")
public class PatientController {

    private static final String HISTORY_PAGE = "patient/history";
    private static final String LIST_VIEW_PAGE = "patient/list";
    private static final String USER_CMD = "user";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientHelper patientHelper;

    @Autowired
    private AccessManager accessManager;

    @InitBinder
    private void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/list")
    public String showList(ModelMap model, HttpServletRequest request) {
        accessManager.checkPatientListAccess(request);

        setupReferenceDataForList(model, request);

        return LIST_VIEW_PAGE;
    }

    @GetMapping("/history")
    public String showHistory(@RequestParam(defaultValue = "0") long id,
                              ModelMap model,
                              HttpServletRequest request) {

        accessManager.checkPatientHistoryAccess(id, request);

        setupReferenceDataForHistory(id, model, request);

        return HISTORY_PAGE;
    }

    private void setupReferenceDataForList(ModelMap model, HttpServletRequest request) {
        User user = getUser(request);

        Set<Patient> patients = patientHelper.getPatientsForDoctor(user.getDoctor());

        model.put("patients", patients);
    }

    private void setupReferenceDataForHistory(long id, ModelMap model, HttpServletRequest request) {
        Patient patient = patientService.findById(id);
        User user = getUser(request);

        List<Prescription> doctorSpecificPrescriptions = patientHelper.getPrescriptions(patient, user.getDoctor(), true);
        List<Prescription> otherPrescriptions = patientHelper.getPrescriptions(patient, user.getDoctor(), false);

        model.put("patient", patient);
        model.put("otherPrescriptions", otherPrescriptions);
        model.put("doctorSpecificPrescriptions", doctorSpecificPrescriptions);
    }
}
