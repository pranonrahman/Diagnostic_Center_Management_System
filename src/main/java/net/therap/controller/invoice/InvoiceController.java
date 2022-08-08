package net.therap.controller.invoice;

import net.therap.model.*;
import net.therap.service.*;
import net.therap.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static java.util.Objects.isNull;
import static net.therap.controller.invoice.InvoiceController.INVOICE_CMD;
import static net.therap.model.Action.*;
import static net.therap.model.RoleEnum.ADMIN;
import static net.therap.model.RoleEnum.RECEPTIONIST;

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

    @GetMapping("/view")
    public String view(@RequestParam Long id, ModelMap model) {
        model.addAttribute(INVOICE_VIEW_CMD,invoiceService.findById(id));
        model.put("action", VIEW);

        return VIEW_PAGE;
    }

    @GetMapping
    public String review(ModelMap model) {
        InvoiceViewModel invoice = (InvoiceViewModel) model.get(INVOICE_CMD);

        if(isNull(invoice) || isNull(invoice.getPatient())) {
            return REDIRECT_DOCTOR_PAGE;
        }

        model.put(INVOICE_VIEW_CMD, invoiceService.getInvoiceFromViewModel(invoice));
        model.put("action", REVIEW);

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String list(ModelMap modelMap) {
        modelMap.addAttribute("invoices", invoiceService.findAll());

        return LIST_VIEW_PAGE;
    }

    @PostMapping
    public String save(@SessionAttribute(INVOICE_CMD) InvoiceViewModel invoice,
                       RedirectAttributes ra,
                       WebRequest webRequest,
                       SessionStatus status,
                       HttpServletRequest request,
                       ModelMap model) {

        Invoice readyToSaveInvoice = invoiceService.getInvoiceFromViewModel(invoice);

        if(readyToSaveInvoice.getParticulars().isEmpty()) {
            model.put("errorMessage", "No Service or product selected!");

            return VIEW_PAGE;
        }

        Person person = (Person) request.getSession().getAttribute("user");
        Role personRole = (Role) request.getSession().getAttribute("role");

        if(isNull(person) || !(personRole.getName().equals(RECEPTIONIST) || personRole.getName().equals(ADMIN))){
            model.put("errorMessage", "User is not authorized");

            return VIEW_PAGE;
        }

        for (Doctor doctor : invoice.getDoctors()) {
            Prescription prescription = new Prescription();
            prescription.setPatient(invoice.getPatient());
            prescription.setDoctor(doctor);
            prescription.setDateOfVisit(new Date());

            prescriptionService.saveOrUpdate(prescription);
        }

        readyToSaveInvoice.setGeneratedBy(person);
        Invoice savedInvoice = invoiceService.saveOrUpdate(readyToSaveInvoice);

        if(model.containsAttribute(INVOICE_CMD)) {
            status.setComplete();
            webRequest.removeAttribute(INVOICE_CMD, WebRequest.SCOPE_SESSION);
        }

        ra.addAttribute("id", savedInvoice.getId());

        return REDIRECT_VIEW_PAGE;
    }
}
