package net.therap.controller.invoice;

import net.therap.command.InvoiceCmd;
import net.therap.command.MedicineItemCmd;
import net.therap.command.RemoveCmd;
import net.therap.editor.MedicineEditor;
import net.therap.entity.Action;
import net.therap.entity.Medicine;
import net.therap.service.MedicineService;
import net.therap.validator.MedicineItemCmdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;
import static net.therap.controller.invoice.InvoiceController.INVOICE_CMD;
import static net.therap.entity.Action.*;

/**
 * @author khandaker.maruf
 * @since 8/7/22
 */
@Controller
@RequestMapping("/invoice/medicine")
@SessionAttributes(INVOICE_CMD)
public class MedicineItemController {

    public static final String INVOICE_CMD = "invoice";
    public static final String MEDICINE_CMD = "medicineItemCmd";
    private static final String ADD_MEDICINE_PAGE = "/invoice/addMedicine";
    private static final String REDIRECT_MEDICINE_PAGE = "redirect:/invoice/medicine";
    private static final String REDIRECT_FACILITY_PAGE = "redirect:/invoice/facility";
    private static final String REDIRECT_DOCTOR_PAGE = "redirect:/invoice/doctor";

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineEditor medicineEditor;

    @Autowired
    private MedicineItemCmdValidator medicineItemCmdValidator;

    @InitBinder("medicineItemCmd")
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Medicine.class, medicineEditor);
        binder.addValidators(medicineItemCmdValidator);
    }

    @GetMapping
    public String view(ModelMap model) {
        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);

        if(isNull(invoice) || isNull(invoice.getPatient())) {
            return REDIRECT_DOCTOR_PAGE;
        }

        setUpReferenceData(VIEW, model);

        return ADD_MEDICINE_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(MEDICINE_CMD) MedicineItemCmd medicineItemCmd,
                               BindingResult result,
                               @RequestParam Action action,
                               ModelMap model) {

        if(action.equals(NEXT)) {
            return REDIRECT_FACILITY_PAGE;
        }

        if(result.hasErrors()) {
            setUpReferenceData(SAVE, model);

            return ADD_MEDICINE_PAGE;
        }

        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        invoice.getMedicines().removeIf(facility -> facility.getMedicine().getId() == medicineItemCmd.getMedicine().getId());
        invoice.getMedicines().add(medicineItemCmd);

        if(action.equals(ADD)) {
            return REDIRECT_MEDICINE_PAGE;
        }

        return REDIRECT_FACILITY_PAGE;
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute("removeModel") RemoveCmd removeCmd, ModelMap model) {
        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);
        invoice.getMedicines().removeIf(medicineItemCmd -> medicineItemCmd.getMedicine().getId() == removeCmd.getId());

        return REDIRECT_MEDICINE_PAGE;
    }

    private void setUpReferenceData(Action action, ModelMap model) {
        if(action.equals(VIEW)) {
            model.put(MEDICINE_CMD, new MedicineItemCmd());
        }

        model.put("medicines", medicineService.findAll());
    }
}
