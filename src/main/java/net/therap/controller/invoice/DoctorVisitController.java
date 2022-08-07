package net.therap.controller.invoice;

import net.therap.editor.DoctorEditor;
import net.therap.editor.PatientEditor;
import net.therap.model.*;
import net.therap.service.DoctorService;
import net.therap.service.PatientService;
import net.therap.viewModel.DoctorVisit;
import net.therap.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static net.therap.controller.invoice.DoctorVisitController.INVOICE_CMD;
import static net.therap.model.Action.SAVE;
import static net.therap.model.Action.VIEW;

/**
 * @author khandaker.maruf
 * @since 8/7/22
 */
@Controller
@RequestMapping("/invoice/doctor")
@SessionAttributes(INVOICE_CMD)
public class DoctorVisitController {

    private static final String ADD_DOCTOR_PAGE = "/invoice/addDoctor";
    private static final String DOCTOR_VISIT_CMD = "doctorVisit";
    public static final String INVOICE_CMD = "invoice";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientEditor patientEditor;

    @Autowired
    private DoctorEditor doctorEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Patient.class, patientEditor);
        binder.registerCustomEditor(Doctor.class, doctorEditor);
    }

    @GetMapping
    public String view(ModelMap model) {
        setUpReferenceData(model, VIEW);

        return ADD_DOCTOR_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(DOCTOR_VISIT_CMD) DoctorVisit doctorVisit,
                             BindingResult result,
                             @SessionAttribute(INVOICE_CMD) InvoiceViewModel invoice,
                             ModelMap model) {

        if(result.hasErrors()) {
            setUpReferenceData(model, SAVE);
            return ADD_DOCTOR_PAGE;
        }

        invoice.setDoctors(doctorVisit.getDoctors());
        invoice.setPatient(doctorVisit.getPatient());

        return "redirect:/invoice/medicine";
    }

    private void setUpReferenceData(ModelMap model, Action action) {
        if(action.equals(VIEW)) {
            if(!model.containsAttribute(INVOICE_CMD)) {
                model.put(INVOICE_CMD, new InvoiceViewModel());
            }
            InvoiceViewModel invoice = (InvoiceViewModel) model.get(INVOICE_CMD);
            model.put(DOCTOR_VISIT_CMD, new DoctorVisit(invoice.getPatient(), invoice.getDoctors()));

        }

        model.put("patients", patientService.findAll());
        model.put("doctors", doctorService.findAll());
    }
}