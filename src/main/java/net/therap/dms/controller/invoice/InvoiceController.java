package net.therap.dms.controller.invoice;

import net.therap.dms.command.InvoiceCmd;
import net.therap.dms.entity.Invoice;
import net.therap.dms.entity.User;
import net.therap.dms.helper.InvoiceHelper;
import net.therap.dms.service.AccessManager;
import net.therap.dms.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static net.therap.dms.constant.URL.INVOICE_DOCTOR;
import static net.therap.dms.constant.URL.SUCCESS;
import static net.therap.dms.controller.invoice.InvoiceController.INVOICE_CMD;
import static net.therap.dms.entity.Action.REVIEW;
import static net.therap.dms.entity.Action.VIEW;
import static net.therap.dms.util.SessionUtil.getUser;
import static net.therap.dms.util.WebUtil.redirect;

/**
 * @author khandaker.maruf
 * @since 8/3/22
 */
@Controller
@RequestMapping("/invoice")
@SessionAttributes(INVOICE_CMD)
public class InvoiceController {

    public static final String INVOICE_CMD = "invoice";
    public static final String INVOICE_VIEW_CMD = "invoiceView";

    private static final String VIEW_PAGE = "/invoice/view";
    private static final String LIST_VIEW_PAGE = "/invoice/list";

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private MessageSourceAccessor msa;

    @Autowired
    private AccessManager accessManager;

    @Autowired
    private InvoiceHelper invoiceHelper;

    @GetMapping
    public String view(@RequestParam(defaultValue = "0") Long id, HttpServletRequest request, ModelMap model) {
        if (id == 0) {
            return review(request, model);
        }

        Invoice invoice = invoiceService.findById(id);
        accessManager.checkInvoiceDetailsAccess(invoice, request);

        setUpReferenceData(invoice, model);

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "0") long patientId, ModelMap model, HttpServletRequest request) {
        accessManager.checkInvoiceListAccess(patientId, request);

        setUpReferenceData(patientId, model, request);

        return LIST_VIEW_PAGE;
    }

    @GetMapping("/save")
    public String save(@SessionAttribute(INVOICE_CMD) InvoiceCmd invoiceCmd,
                       SessionStatus sessionStatus,
                       ModelMap model,
                       HttpServletRequest request) {

        accessManager.checkInvoiceWriteAccess(request);

        if (invoiceHelper.invoiceNotCreated(model)) {
            return redirect(INVOICE_DOCTOR);
        }

        User user = getUser(request);
        Invoice invoice = invoiceService.getInvoiceFromCmd(invoiceCmd, user.getReceptionist());

        if (invoice.getParticulars().isEmpty()) {
            model.put("errorMessage", msa.getMessage("error.notSelected"));

            return VIEW_PAGE;
        }

        invoiceService.saveOrUpdate(invoice, invoiceCmd);

        sessionStatus.setComplete();

        return redirect(SUCCESS);
    }

    private String review(HttpServletRequest request, ModelMap model) {
        if (invoiceHelper.invoiceNotCreated(model)) {
            return redirect(INVOICE_DOCTOR);
        }

        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        setUpReferenceData(invoice, model, request);

        return VIEW_PAGE;
    }

    private void setUpReferenceData(Invoice invoice, ModelMap model) {
        model.addAttribute(INVOICE_VIEW_CMD, invoice);
        model.put("action", VIEW);
    }

    private void setUpReferenceData(InvoiceCmd invoice, ModelMap model, HttpServletRequest request) {
        User user = getUser(request);
        invoice.setReceptionist(user.getReceptionist());

        model.put(INVOICE_VIEW_CMD, invoiceService.getInvoiceFromCmd(invoice, user.getReceptionist()));
        model.put("action", REVIEW);
    }

    private void setUpReferenceData(long patientId, ModelMap model, HttpServletRequest request) {
        User user = getUser(request);

        List<Invoice> invoices;
        boolean isPatient = patientId != 0 && patientId == user.getPatient().getId() ;

        if (isPatient) {
            invoices = invoiceService.findByPatient(user.getPatient());

        } else {
            invoices = invoiceService.findAll();
        }

        model.addAttribute("invoices", invoices);
        model.addAttribute("isPatient", isPatient);
    }
}
