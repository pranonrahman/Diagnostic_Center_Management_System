package net.therap.dms.controller;

import net.therap.dms.editor.FacilityEditor;
import net.therap.dms.entity.Facility;
import net.therap.dms.entity.Prescription;
import net.therap.dms.entity.User;
import net.therap.dms.helper.DoctorHelper;
import net.therap.dms.helper.PrescriptionHelper;
import net.therap.dms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Objects.isNull;
import static net.therap.dms.controller.PrescriptionController.PRESCRIPTION_CMD;
import static net.therap.dms.util.SessionUtil.getUser;
import static net.therap.dms.util.WebUtil.redirect;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
@RequestMapping("/prescription")
@SessionAttributes(PRESCRIPTION_CMD)
public class PrescriptionController {

    public static final String PRESCRIPTION_CMD = "prescription";
    private static final String VIEW_PAGE = "prescription/form";
    private static final String LIST_VIEW_PAGE = "prescription/list";
    private static final String SUCCESS = "success";

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private FacilityEditor facilityEditor;

    @Autowired
    private PrescriptionHelper prescriptionHelper;

    @Autowired
    private DoctorHelper doctorHelper;

    @Autowired
    private AccessManager accessManager;

    @InitBinder
    private void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Facility.class, facilityEditor);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.setDisallowedFields("id", "doctor", "patient");
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
    public String process(@ModelAttribute(PRESCRIPTION_CMD) Prescription prescription,
                          BindingResult result,
                          ModelMap model,
                          SessionStatus sessionStatus,
                          HttpServletRequest request) {

        accessManager.checkPrescriptionViewAccess(prescription, request);

        if (result.hasErrors()) {
            setupReferenceDataForView(prescription, model, request);

            return VIEW_PAGE;
        }

        prescriptionService.saveOrUpdate(prescription);

        sessionStatus.setComplete();

        return redirect(SUCCESS);
    }

    private void setupReferenceDataForView(Prescription prescription,
                                           ModelMap model,
                                           HttpServletRequest request) {

        model.put("facilities", facilityService.findAll());
        model.put(PRESCRIPTION_CMD, isNull(prescription) ? new Prescription() : prescription);
        model.put("readonly", !doctorHelper.hasPrescription(prescription, request));
    }

    private void setupReferenceDataForList(ModelMap model, HttpServletRequest request) {
        User user = getUser(request);

        List<Prescription> prescriptions = prescriptionHelper.getPrescriptionsForPatient(user.getPatient());

        model.put("prescriptions", prescriptions);
    }
}
