package net.therap.controller;

import net.therap.editor.FacilityEditor;
import net.therap.model.*;
import net.therap.service.DoctorService;
import net.therap.service.FacilityService;
import net.therap.service.PatientService;
import net.therap.service.PrescriptionService;
import net.therap.viewModel.PrescriptionViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
@SessionAttributes({"role", "user"})
@RequestMapping("/prescription")
public class PrescriptionController {

    private static final String VIEW_PAGE = "prescription/form";

    private static final String PRESCRIPTION_LIST_VIEW_PAGE = "prescription/list";

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
    }

    @GetMapping("/view")
    public String loadViewPage(@ModelAttribute("user") User user, @ModelAttribute("role") Role role, @RequestParam("id") String id, ModelMap model) {
        model.put("doctorId", role.getName().equals(RoleEnum.DOCTOR) ? user.getDoctor().getId() : 0);
        setupReferenceData(model, Long.parseLong(id));

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String loadPrescriptionList(@ModelAttribute("user") User user, ModelMap model) {
        Patient patient = user.getPatient();
        List<PrescriptionViewModel> prescriptionViewModels = new ArrayList<>();
        Set<Prescription> prescriptions = patient.getPrescriptions();

        for (Prescription prescription : prescriptions) {
            prescriptionViewModels.add(new PrescriptionViewModel(prescription));
        }

        Collections.sort(prescriptionViewModels);

        model.put("patientName", user.getName());
        model.put("prescriptionViewModels", prescriptionViewModels);

        return PRESCRIPTION_LIST_VIEW_PAGE;
    }

    @GetMapping("/save")
    public String loadEditPage(@RequestParam("id") String id, ModelMap model) {
        model.put("action", "edit");
        setupReferenceData(model, Long.parseLong(id));

        return VIEW_PAGE;
    }

    @PostMapping("/save")
    public String processEdit(@ModelAttribute("prescription") Prescription prescription, ModelMap model) {
        prescription.setPatient(patientService.findById(prescription.getPatient().getId()));
        prescription.setDoctor(doctorService.findById(prescription.getDoctor().getId()));
        prescription.setDateOfVisit(new Date());

        prescriptionService.saveOrUpdate(prescription);
        setupReferenceData(model, 0);

        return "redirect:/prescription/view?id=" + prescription.getId();
    }

    private void setupReferenceData(ModelMap model, long prescriptionId) {
        model.put("facilities", facilityService.findAll());

        if (prescriptionId == 0) {
            model.put("prescription", new Prescription());
        } else {
            model.put("prescription", prescriptionService.findById(prescriptionId));
        }
    }
}
