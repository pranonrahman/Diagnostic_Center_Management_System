package net.therap.controller;

import net.therap.editor.FacilityEditor;
import net.therap.entity.Facility;
import net.therap.entity.Patient;
import net.therap.entity.Prescription;
import net.therap.entity.User;
import net.therap.exception.InsufficientAccessException;
import net.therap.service.DoctorService;
import net.therap.service.FacilityService;
import net.therap.service.PatientService;
import net.therap.service.PrescriptionService;
import net.therap.util.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.therap.controller.PatientController.USER_CMD;
import static net.therap.entity.RoleEnum.DOCTOR;
import static net.therap.entity.RoleEnum.PATIENT;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
@SessionAttributes(USER_CMD)
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

    @InitBinder
    private void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Facility.class, facilityEditor);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String show(@RequestParam(value = "id", defaultValue = "0") long id,
                       ModelMap model) {

        Prescription prescription = prescriptionService.findById(id);
        User user = (User) model.getAttribute(USER_CMD);

        if (nonNull(user) &&
                RoleUtil.userContains(user, PATIENT) &&
                prescription.getPatient().getId() != user.getId()) {

            throw new InsufficientAccessException();
        }

        setupReferenceData(prescription, user, model);

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String showList(ModelMap model) {
        User user = (User) model.getAttribute(USER_CMD);
        Patient patient = user.getPatient();
        List<Prescription> prescriptions = patient.getPrescriptions();

        Collections.sort(prescriptions);

        setupReferenceData(prescriptions, model);

        return LIST_VIEW_PAGE;
    }

    @PostMapping
    public String process(@ModelAttribute("prescription") Prescription prescription,
                          RedirectAttributes redirectAttributes) {

        prescription.setPatient(patientService.findById(prescription.getPatient().getId()));
        prescription.setDoctor(doctorService.findById(prescription.getDoctor().getId()));

        prescriptionService.saveOrUpdate(prescription);

        redirectAttributes.addAttribute("id", prescription.getId());
        redirectAttributes.addAttribute("success", true);

        return "redirect:/prescription";
    }

    private void setupReferenceData(Prescription prescription, User user, ModelMap model) {
        model.put("facilities", facilityService.findAll());
        model.put("prescription", isNull(prescription) ? new Prescription() : prescription);
        model.put("readonly", !RoleUtil.userContains(user, DOCTOR) ||
                (user.getDoctor().getId() != prescription.getDoctor().getId()));
    }

    private void setupReferenceData(List<Prescription> prescriptions, ModelMap model) {
        model.put("prescriptions", prescriptions);
    }
}
