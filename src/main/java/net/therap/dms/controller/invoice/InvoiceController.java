package net.therap.dms.controller.invoice;

import net.therap.dms.command.InvoiceCmd;
import net.therap.dms.command.MedicineItemCmd;
import net.therap.dms.entity.*;
import net.therap.dms.exception.InsufficientAccessException;
import net.therap.dms.service.InvoiceService;
import net.therap.dms.service.MedicineService;
import net.therap.dms.service.PrescriptionService;
import net.therap.dms.util.RoleUtil;
import net.therap.dms.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static net.therap.dms.controller.invoice.InvoiceController.INVOICE_CMD;
import static net.therap.dms.entity.Action.REVIEW;
import static net.therap.dms.entity.Action.VIEW;
import static net.therap.dms.entity.RoleEnum.PATIENT;
import static net.therap.dms.entity.RoleEnum.RECEPTIONIST;

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

    @GetMapping
    public String view(@RequestParam(defaultValue = "0") Long id, HttpServletRequest request, ModelMap model) {
        if (id == 0) {
            return review(request, model);
        }

        checkAccessForView(id, request);

        setUpReferenceData(id, model);

        return VIEW_PAGE;
    }

    private String review(HttpServletRequest request, ModelMap model) {
        if (noInvoiceGenerated(model)) {
            return CommonUtil.getRedirectUrl(INVOICE_DOCTOR);
        }

        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        setUpReferenceData(request, invoice, model);

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "0") long patientId, ModelMap model, HttpServletRequest request) {
        checkAccessForList(request, patientId);

        setUpReferenceData(request, patientId, model);

        return LIST_VIEW_PAGE;
    }

    @PostMapping
    public String save(@SessionAttribute(INVOICE_CMD) InvoiceCmd invoiceCmd,
                       RedirectAttributes ra,
                       WebRequest webRequest,
                       SessionStatus status,
                       HttpServletRequest request,
                       ModelMap model) {

        User user = SessionUtil.getUser(request);

        if (isUnauthorizedToSaveInvoice(user)) {
            model.put("errorMessage", msa.getMessage("error.unAuthorized"));

            return VIEW_PAGE;
        }

        invoiceCmd.setReceptionist(user.getReceptionist());
        Invoice invoice = invoiceService.getInvoiceFromCmd(invoiceCmd);

        if (invoice.getParticulars().isEmpty()) {
            model.put("errorMessage", msa.getMessage("error.notSelected"));

            return VIEW_PAGE;
        }

        invoiceService.createEmptyPrescriptions(invoiceCmd);
        invoiceService.updateMedicineQuantity(invoiceCmd);

        Invoice savedInvoice = invoiceService.saveOrUpdate(invoice);

        SessionUtil.removeInvoice(model, status, webRequest);

        ra.addAttribute("id", savedInvoice.getId());

        return CommonUtil.getRedirectUrl(VIEW_PAGE);
    }

    private boolean isUnauthorizedToSaveInvoice(User user) {
        return isNull(user) || user.getRoles().stream().noneMatch(role -> role.getName().equals(RECEPTIONIST));
    }

    private boolean noInvoiceGenerated(ModelMap model) {
        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);

        return isNull(invoice) || isNull(invoice.getPatient());
    }

    private void checkAccessForView(Long id, HttpServletRequest request) {
        User user = SessionUtil.getUser(request);
        Invoice invoice = invoiceService.findById(id);

        if (!(RoleUtil.userContains(user, RECEPTIONIST) ||
                (RoleUtil.userContains(user, PATIENT) && invoice.getPatient().getUser().getId() == user.getId()))) {

            throw new InsufficientAccessException();
        }
    }

    private void checkAccessForList(HttpServletRequest request, long patientId) {
        User user = SessionUtil.getUser(request);

        if (!(RoleUtil.userContains(user, RECEPTIONIST)
                || (RoleUtil.userContains(user, PATIENT) && user.getId() == patientId))) {

            throw new InsufficientAccessException();
        }
    }

    private void setUpReferenceData(long id, ModelMap model) {
        model.addAttribute(INVOICE_VIEW_CMD, invoiceService.findById(id));
        model.put("action", VIEW);
    }

    private void setUpReferenceData(HttpServletRequest request, InvoiceCmd invoice, ModelMap model) {
        User user = SessionUtil.getUser(request);
        invoice.setReceptionist(user.getReceptionist());

        model.put(INVOICE_VIEW_CMD, invoiceService.getInvoiceFromCmd(invoice));
        model.put("action", REVIEW);
    }

    private void setUpReferenceData(HttpServletRequest request, long patientId, ModelMap model) {
        User user = SessionUtil.getUser(request);

        List<Invoice> invoices = new ArrayList<>();
        boolean isPatient = false;

        if (patientId == 0) {
            invoices = invoiceService.findAll();

        } else if (patientId == user.getPatient().getId()) {
            invoices = invoiceService.findByPatient(user.getPatient());
            isPatient = true;
        }

        model.addAttribute("invoices", invoices);
        model.addAttribute("isPatient", isPatient);
    }
}
