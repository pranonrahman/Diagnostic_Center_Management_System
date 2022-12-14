package net.therap.dms.controller.invoice;

import net.therap.dms.command.FacilityItemCmd;
import net.therap.dms.command.InvoiceCmd;
import net.therap.dms.editor.FacilityEditor;
import net.therap.dms.entity.Action;
import net.therap.dms.entity.Facility;
import net.therap.dms.helper.InvoiceHelper;
import net.therap.dms.service.AccessManager;
import net.therap.dms.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static net.therap.dms.constant.URL.*;
import static net.therap.dms.controller.invoice.InvoiceController.INVOICE_CMD;
import static net.therap.dms.entity.Action.*;
import static net.therap.dms.util.WebUtil.redirect;

/**
 * @author khandaker.maruf
 * @since 8/7/22
 */
@Controller
@RequestMapping("/invoice/facility")
@SessionAttributes(INVOICE_CMD)
public class FacilityItemController {

    public static final String FACILITY_CMD = "facilityItemCmd";

    private static final String ADD_FACILITY_PAGE = "/invoice/addFacility";

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private FacilityEditor facilityEditor;

    @Autowired
    private AccessManager accessManager;

    @Autowired
    private InvoiceHelper invoiceHelper;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Facility.class, facilityEditor);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String view(ModelMap model, HttpServletRequest request) {
        accessManager.checkInvoiceWriteAccess(request);

        if (invoiceHelper.invoiceNotCreated(model)) {
            return redirect(INVOICE_DOCTOR);
        }

        setUpReferenceData(VIEW, model);

        return ADD_FACILITY_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(FACILITY_CMD) FacilityItemCmd facilityItemCmd,
                       BindingResult result,
                       @RequestParam("action") Action action,
                       ModelMap model,
                       HttpServletRequest request) {

        accessManager.checkInvoiceWriteAccess(request);

        if (action.equals(NEXT)) {
            return redirect(INVOICE);
        }

        if (result.hasErrors()) {
            setUpReferenceData(SAVE, model);

            return ADD_FACILITY_PAGE;
        }

        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        invoice.getFacilities().removeIf(facility -> facility.getFacility().getId() == facilityItemCmd.getFacility().getId());
        invoice.getFacilities().add(facilityItemCmd);

        if (action.equals(ADD)) {
            return redirect(INVOICE_FACILITY);
        }

        return redirect(INVOICE);
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") long facilityId, ModelMap model, HttpServletRequest request) {
        accessManager.checkInvoiceWriteAccess(request);

        if (invoiceHelper.invoiceNotCreated(model)) {
            return redirect(INVOICE_DOCTOR);
        }

        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        invoice.getFacilities().removeIf(facilityItem -> facilityItem.getFacility().getId() == facilityId);

        return redirect(INVOICE_FACILITY);
    }

    private void setUpReferenceData(Action action, ModelMap model) {
        if (action.equals(VIEW)) {
            model.put(FACILITY_CMD, new FacilityItemCmd());
        }

        model.put("facilities", facilityService.findAll());
    }
}
