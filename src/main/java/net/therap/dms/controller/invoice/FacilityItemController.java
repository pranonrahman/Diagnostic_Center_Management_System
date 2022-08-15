package net.therap.dms.controller.invoice;

import net.therap.dms.command.FacilityItemCmd;
import net.therap.dms.command.InvoiceCmd;
import net.therap.dms.command.RemoveCmd;
import net.therap.dms.editor.FacilityEditor;
import net.therap.dms.entity.Action;
import net.therap.dms.entity.Facility;
import net.therap.dms.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;
import static net.therap.dms.controller.invoice.InvoiceController.INVOICE_CMD;
import static net.therap.dms.entity.Action.*;

/**
 * @author khandaker.maruf
 * @since 8/7/22
 */
@Controller
@RequestMapping("/invoice/facility")
@SessionAttributes(INVOICE_CMD)
public class FacilityItemController {

    public static final String INVOICE_CMD = "invoice";
    public static final String FACILITY_CMD = "facilityItemCmd";
    private static final String ADD_FACILITY_PAGE = "/invoice/addFacility";
    private static final String REDIRECT_FACILITY_PAGE = "redirect:/invoice/facility";
    private static final String REDIRECT_DOCTOR_PAGE = "redirect:/invoice/doctor";
    private static final String REDIRECT_INVOICE_REVIEW_PAGE = "redirect:/invoice";

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private FacilityEditor facilityEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Facility.class, facilityEditor);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String view(ModelMap model) {
        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);

        if (isNull(invoice) || isNull(invoice.getPatient())) {
            return REDIRECT_DOCTOR_PAGE;
        }

        setUpReferenceData(VIEW, model);

        return ADD_FACILITY_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(FACILITY_CMD) FacilityItemCmd facilityItemCmd,
                       BindingResult result,
                       @RequestParam("action") Action action,
                       ModelMap model) {

        if (action.equals(NEXT)) {
            return REDIRECT_INVOICE_REVIEW_PAGE;
        }

        if (result.hasErrors()) {
            setUpReferenceData(SAVE, model);

            return ADD_FACILITY_PAGE;
        }

        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        invoice.getFacilities().removeIf(facility -> facility.getFacility().getId() == facilityItemCmd.getFacility().getId());
        invoice.getFacilities().add(facilityItemCmd);

        if (action.equals(ADD)) {
            return REDIRECT_FACILITY_PAGE;
        }

        return REDIRECT_INVOICE_REVIEW_PAGE;
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute("removeModel") RemoveCmd removeCmd, ModelMap model) {
        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        invoice.getFacilities().removeIf(medicineItem -> medicineItem.getFacility().getId() == removeCmd.getId());

        return REDIRECT_FACILITY_PAGE;
    }

    private void setUpReferenceData(Action action, ModelMap model) {
        if (action.equals(VIEW)) {
            model.put(FACILITY_CMD, new FacilityItemCmd());
        }

        model.put("facilities", facilityService.findAll());
    }
}