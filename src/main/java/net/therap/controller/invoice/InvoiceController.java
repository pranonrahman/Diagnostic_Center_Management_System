package net.therap.controller.invoice;

import net.therap.model.Invoice;
import net.therap.service.*;
import net.therap.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static net.therap.controller.invoice.InvoiceController.INVOICE_CMD;
import static net.therap.model.Action.*;

/**
 * @author khandaker.maruf
 * @since 8/3/22
 */
@Controller
@RequestMapping("/invoice")
@SessionAttributes(INVOICE_CMD)
public class InvoiceController {

    private static final String VIEW_PAGE = "/invoice/view";
    private static final String LIST_VIEW_PAGE = "/invoice/list";
    private static final String FORM_PAGE = "/invoice/form";
    public static final String INVOICE_CMD = "invoice";
    public static final String INVOICE_VIEW_CMD = "invoiceView";

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/view")
    public String view(@RequestParam long id, ModelMap model) {
        List<Invoice> invoices = invoiceService.findAll();
        Invoice invoice = invoiceService.findById(id);
        model.addAttribute(INVOICE_VIEW_CMD, invoiceService.findById(id));
        model.put("action", VIEW);

        return VIEW_PAGE;
    }

    @GetMapping
    public String review(ModelMap model, @SessionAttribute(INVOICE_CMD) InvoiceViewModel invoice) {
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
                       WebRequest request,
                       SessionStatus status,
                       ModelMap model) {

        Invoice savedInvoice = invoiceService.saveOrUpdate(invoiceService.getInvoiceFromViewModel(invoice));

        if(model.containsAttribute(INVOICE_CMD)) {
            status.setComplete();
            request.removeAttribute(INVOICE_CMD, WebRequest.SCOPE_SESSION);
        }

        ra.addAttribute("id", savedInvoice.getId());
        return "redirect:/invoice/view";
    }
}
