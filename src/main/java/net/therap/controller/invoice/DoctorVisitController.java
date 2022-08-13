package net.therap.controller.invoice;

import net.therap.command.DoctorVisitCmd;
import net.therap.command.InvoiceCmd;
import net.therap.editor.DoctorEditor;
import net.therap.editor.PatientEditor;
import net.therap.entity.Action;
import net.therap.entity.Doctor;
import net.therap.entity.Patient;
import net.therap.service.DoctorService;
import net.therap.service.PatientService;
import net.therap.validator.DoctorVisitCmdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static net.therap.controller.invoice.DoctorVisitController.INVOICE_CMD;
import static net.therap.entity.Action.SAVE;
import static net.therap.entity.Action.VIEW;

/**
 * @author khandaker.maruf
 * @since 8/7/22
 */
@Controller
@RequestMapping("/invoice/doctor")
@SessionAttributes(INVOICE_CMD)
public class DoctorVisitController {

    private static final String ADD_DOCTOR_PAGE = "/invoice/addDoctor";
    private static final String DOCTOR_VISIT_CMD = "doctorVisitCmd";
    public static final String INVOICE_CMD = "invoice";
    public static final String REDIRECT_MEDICINE_PAGE = "redirect:/invoice/medicine";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientEditor patientEditor;

    @Autowired
    private DoctorEditor doctorEditor;

    @Autowired
    DoctorVisitCmdValidator doctorVisitCmdValidator;

    @InitBinder("doctorVisitCmd")
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Patient.class, patientEditor);
        binder.registerCustomEditor(Doctor.class, doctorEditor);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(doctorVisitCmdValidator);
    }

    @GetMapping
    public String view(ModelMap model) {
        setUpReferenceData(VIEW, model);

        return ADD_DOCTOR_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(DOCTOR_VISIT_CMD) DoctorVisitCmd doctorVisitCmd,
                       BindingResult result,
                       @SessionAttribute(INVOICE_CMD) InvoiceCmd invoice,
                       ModelMap model) {

        if (result.hasErrors()) {
            setUpReferenceData(SAVE, model);
            return ADD_DOCTOR_PAGE;
        }

        invoice.setDoctors(doctorVisitCmd.getDoctors());
        invoice.setPatient(doctorVisitCmd.getPatient());

        return REDIRECT_MEDICINE_PAGE;
    }

    private void setUpReferenceData(Action action, ModelMap model) {
        if (action.equals(VIEW)) {
            if (!model.containsAttribute(INVOICE_CMD)) {
                model.put(INVOICE_CMD, new InvoiceCmd());
            }

            InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
            model.put(DOCTOR_VISIT_CMD, new DoctorVisitCmd(invoice.getPatient(), invoice.getDoctors()));
        }

        model.put("patients", patientService.findAll());
        model.put("doctors", doctorService.findAll());
    }
}
