package net.therap.controller.invoice;

import net.therap.command.InvoiceCmd;
import net.therap.command.MedicineItemCmd;
import net.therap.entity.*;
import net.therap.service.InvoiceService;
import net.therap.service.MedicineService;
import net.therap.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static net.therap.controller.invoice.InvoiceController.INVOICE_CMD;
import static net.therap.entity.Action.REVIEW;
import static net.therap.entity.Action.VIEW;
import static net.therap.entity.RoleEnum.ADMIN;
import static net.therap.entity.RoleEnum.RECEPTIONIST;

/**
 * @author khandaker.maruf
 * @since 8/3/22
 */
@Controller
@RequestMapping("/invoice")
@SessionAttributes(INVOICE_CMD)
public class InvoiceController {

    private static final String VIEW_PAGE = "/invoice/view";
    private static final String REDIRECT_VIEW_PAGE = "redirect:/invoice/view";
    private static final String LIST_VIEW_PAGE = "/invoice/list";
    public static final String INVOICE_CMD = "invoice";
    public static final String INVOICE_VIEW_CMD = "invoiceView";
    private static final String REDIRECT_DOCTOR_PAGE = "redirect:/invoice/doctor";

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MessageSourceAccessor msa;

    @GetMapping("/view")
    public String view(@RequestParam Long id, ModelMap model) {
        setUpReferenceData(invoiceService.findById(id), model);

        return VIEW_PAGE;
    }

    @GetMapping
    public String review(ModelMap model) {
        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);

        if (isNull(invoice) || isNull(invoice.getPatient())) {
            return REDIRECT_DOCTOR_PAGE;
        }

        setUpReferenceData(invoice, model);

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String list(HttpServletRequest request, ModelMap model) {
        Role userRole = (Role) request.getSession().getAttribute("role");

        List<Invoice> invoices = invoiceService.findAll();

//        if (userRole.getName().equals(RoleEnum.PATIENT)) {
//            User user = (User) request.getSession().getAttribute("user");
//            Patient patient = user.getPatient();
//            invoices = invoiceService.findByPatient(patient);
//        } else {
//            invoices = invoiceService.findAll();
//        }

        setUpReferenceData(invoices, model);

        return LIST_VIEW_PAGE;
    }

    @PostMapping
    public String save(@SessionAttribute(INVOICE_CMD) InvoiceCmd invoiceCmd,
                       RedirectAttributes ra,
                       WebRequest webRequest,
                       SessionStatus status,
                       HttpServletRequest request,
                       ModelMap model) {

        User user = (User) request.getSession().getAttribute("user");
//        Role userRole = (Role) request.getSession().getAttribute("role");

        if (isNull(user) || !(user.getRoles().contains(new Role(RECEPTIONIST)))) {
            model.put("errorMessage", msa.getMessage("error.unAuthorized"));

            return VIEW_PAGE;
        }

        Invoice invoice = invoiceService.getInvoiceFromCmd(invoiceCmd);

        if (invoice.getParticulars().isEmpty()) {
            model.put("errorMessage", "error.notSelected");

            return VIEW_PAGE;
        }

        createEmptyPrescriptions(invoiceCmd);
        updateMedicineQuantity(invoiceCmd);

        invoice.setGeneratedBy(user);
        Invoice savedInvoice = invoiceService.saveOrUpdate(invoice);

        if (model.containsAttribute(INVOICE_CMD)) {
            status.setComplete();
            webRequest.removeAttribute(INVOICE_CMD, WebRequest.SCOPE_SESSION);
        }

        ra.addAttribute("id", savedInvoice.getId());

        return REDIRECT_VIEW_PAGE;
    }

    private void createEmptyPrescriptions(InvoiceCmd invoiceCmd) {
        for (Doctor doctor : invoiceCmd.getDoctors()) {
            Prescription prescription = new Prescription();
            prescription.setPatient(invoiceCmd.getPatient());
            prescription.setDoctor(doctor);
            prescription.setDateOfVisit(new Date());

            prescriptionService.saveOrUpdate(prescription);
        }
    }

    private void updateMedicineQuantity(InvoiceCmd invoiceCmd) {
        for (MedicineItemCmd medicineItem : invoiceCmd.getMedicines()) {
            Medicine updatedMedicine = medicineItem.getMedicine();
            updatedMedicine.setAvailableUnits(updatedMedicine.getAvailableUnits() - medicineItem.getQuantity());
            medicineService.saveOrUpdate(updatedMedicine);
        }
    }

    private void setUpReferenceData(Invoice invoice, ModelMap model) {
        model.addAttribute(INVOICE_VIEW_CMD, invoice);
        model.put("action", VIEW);
    }

    private void setUpReferenceData(InvoiceCmd invoice, ModelMap model) {
        model.put(INVOICE_VIEW_CMD, invoiceService.getInvoiceFromCmd(invoice));
        model.put("action", REVIEW);
    }

    private void setUpReferenceData(List<Invoice> invoices, ModelMap model) {
        model.addAttribute("invoices", invoices);
    }
}
