package net.therap.dms.controller.invoice;

import net.therap.dms.command.InvoiceCmd;
import net.therap.dms.command.MedicineItemCmd;
import net.therap.dms.editor.MedicineEditor;
import net.therap.dms.entity.Action;
import net.therap.dms.entity.Medicine;
import net.therap.dms.helper.InvoiceHelper;
import net.therap.dms.service.AccessManager;
import net.therap.dms.service.MedicineService;
import net.therap.dms.validator.MedicineItemCmdValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/invoice/medicine")
@SessionAttributes(INVOICE_CMD)
public class MedicineItemController {

    public static final String MEDICINE_CMD = "medicineItemCmd";

    private static final String ADD_MEDICINE_PAGE = "/invoice/addMedicine";

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineEditor medicineEditor;

    @Autowired
    private MedicineItemCmdValidator medicineItemCmdValidator;

    @Autowired
    private AccessManager accessManager;

    @Autowired
    private InvoiceHelper invoiceHelper;

    @InitBinder("medicineItemCmd")
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Medicine.class, medicineEditor);
        binder.addValidators(medicineItemCmdValidator);
    }

    @GetMapping
    public String view(ModelMap model, HttpServletRequest request) {
        accessManager.checkInvoiceWriteAccess(request);

        if (invoiceHelper.invoiceNotCreated(model)) {
            return redirect(INVOICE_DOCTOR);
        }

        setUpReferenceData(VIEW, model);

        return ADD_MEDICINE_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(MEDICINE_CMD) MedicineItemCmd medicineItemCmd,
                       BindingResult result,
                       @RequestParam Action action,
                       ModelMap model,
                       HttpServletRequest request) {

        accessManager.checkInvoiceWriteAccess(request);

        if (action.equals(NEXT)) {
            return redirect(INVOICE_FACILITY);
        }

        if (result.hasErrors()) {
            setUpReferenceData(SAVE, model);

            return ADD_MEDICINE_PAGE;
        }

        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        invoice.getMedicines().removeIf(facility -> facility.getMedicine().getId() == medicineItemCmd.getMedicine().getId());
        invoice.getMedicines().add(medicineItemCmd);

        if (action.equals(ADD)) {
            return redirect(INVOICE_MEDICINE);
        }

        return redirect(INVOICE_FACILITY);
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") long medicineId, ModelMap model, HttpServletRequest request) {
        accessManager.checkInvoiceWriteAccess(request);

        if (invoiceHelper.invoiceNotCreated(model)) {
            return redirect(INVOICE_DOCTOR);
        }

        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        invoice.getMedicines().removeIf(medicineItemCmd -> medicineItemCmd.getMedicine().getId() == medicineId);

        return redirect(INVOICE_MEDICINE);
    }

    private void setUpReferenceData(Action action, ModelMap model) {
        if (action.equals(VIEW)) {
            model.put(MEDICINE_CMD, new MedicineItemCmd());
        }

        model.put("medicines", medicineService.findAll());
    }
}
