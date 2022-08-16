package net.therap.dms.controller.invoice;

import net.therap.dms.command.DoctorVisitCmd;
import net.therap.dms.command.InvoiceCmd;
import net.therap.dms.editor.DoctorEditor;
import net.therap.dms.editor.PatientEditor;
import net.therap.dms.entity.Action;
import net.therap.dms.entity.Doctor;
import net.therap.dms.entity.Patient;
import net.therap.dms.service.AccessManager;
import net.therap.dms.service.DoctorService;
import net.therap.dms.service.PatientService;
import net.therap.dms.util.WebUtil;
import net.therap.dms.validator.DoctorVisitCmdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static net.therap.dms.constant.URL.INVOICE_MEDICINE;
import static net.therap.dms.controller.invoice.DoctorVisitController.INVOICE_CMD;
import static net.therap.dms.entity.Action.SAVE;
import static net.therap.dms.entity.Action.VIEW;

/**
 * @author khandaker.maruf
 * @since 8/7/22
 */
@Controller
@RequestMapping("/invoice/doctor")
@SessionAttributes(INVOICE_CMD)
public class DoctorVisitController {

    public static final String INVOICE_CMD = "invoice";
    private static final String DOCTOR_VISIT_CMD = "doctorVisitCmd";

    private static final String ADD_DOCTOR_PAGE = "/invoice/addDoctor";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientEditor patientEditor;

    @Autowired
    private DoctorEditor doctorEditor;

    @Autowired
    private DoctorVisitCmdValidator doctorVisitCmdValidator;

    @Autowired
    private AccessManager accessManager;

    @InitBinder("doctorVisitCmd")
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Patient.class, patientEditor);
        binder.registerCustomEditor(Doctor.class, doctorEditor);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(doctorVisitCmdValidator);
    }

    @GetMapping
    public String view(ModelMap model, HttpServletRequest request) {
        accessManager.checkInvoiceWriteAccess(request);

        setUpReferenceData(VIEW, model);

        return ADD_DOCTOR_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(DOCTOR_VISIT_CMD) DoctorVisitCmd doctorVisitCmd,
                       BindingResult result,
                       @SessionAttribute(INVOICE_CMD) InvoiceCmd invoice,
                       ModelMap model,
                       HttpServletRequest request) {

        accessManager.checkInvoiceWriteAccess(request);

        if (result.hasErrors()) {
            setUpReferenceData(SAVE, model);
            return ADD_DOCTOR_PAGE;
        }

        invoice.setDoctors(doctorVisitCmd.getDoctors());
        invoice.setPatient(doctorVisitCmd.getPatient());

        return WebUtil.redirect(INVOICE_MEDICINE);
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
