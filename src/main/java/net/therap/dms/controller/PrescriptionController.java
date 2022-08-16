package net.therap.dms.controller;

import net.therap.dms.editor.FacilityEditor;
import net.therap.dms.entity.Facility;
import net.therap.dms.entity.Prescription;
import net.therap.dms.entity.User;
import net.therap.dms.helper.PrescriptionHelper;
import net.therap.dms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Objects.isNull;
import static net.therap.dms.entity.RoleEnum.DOCTOR;
import static net.therap.dms.util.RoleUtil.userContains;
import static net.therap.dms.util.SessionUtil.getUser;
import static net.therap.dms.util.WebUtil.redirect;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

    private static final String VIEW_PAGE = "prescription/form";

    private static final String LIST_VIEW_PAGE = "prescription/list";

    private static final String USER_CMD = "user";

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private FacilityEditor facilityEditor;

    @Autowired
    private PrescriptionHelper prescriptionHelper;

    @Autowired
    private AccessManager accessManager;

    @InitBinder
    private void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Facility.class, facilityEditor);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String show(@RequestParam(defaultValue = "0") long id,
                       ModelMap model,
                       HttpServletRequest request) {

        Prescription prescription = prescriptionService.findById(id);

        accessManager.checkPrescriptionViewAccess(prescription, request);

        setupReferenceDataForView(prescription, model, request);

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String showList(ModelMap model, HttpServletRequest request) {
        accessManager.checkPrescriptionListAccess(request);

        setupReferenceDataForList(model, request);

        return LIST_VIEW_PAGE;
    }

    @PostMapping
    public String process(@ModelAttribute("prescription") Prescription prescription,
                          HttpServletRequest request) {

        prescription.setPatient(patientService.findById(prescription.getPatient().getId()));
        prescription.setDoctor(doctorService.findById(prescription.getDoctor().getId()));

        accessManager.checkPrescriptionViewAccess(prescription, request);

        prescriptionService.saveOrUpdate(prescription);

        return redirect("success");
    }

    private void setupReferenceDataForView(Prescription prescription, ModelMap model, HttpServletRequest request) {
        User user = getUser(request);

        model.put("facilities", facilityService.findAll());
        model.put("prescription", isNull(prescription) ? new Prescription() : prescription);
        model.put("readonly", !userContains(user, DOCTOR) ||
                (user.getDoctor().getId() != prescription.getDoctor().getId()));
    }

    private void setupReferenceDataForList(ModelMap model, HttpServletRequest request) {
        User user = getUser(request);

        List<Prescription> prescriptions = prescriptionHelper.getPrescriptionsForPatient(user.getPatient());

        model.put("prescriptions", prescriptions);
    }
}
