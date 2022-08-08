package net.therap.controller.invoice;

import net.therap.editor.MedicineEditor;
import net.therap.model.*;
import net.therap.service.MedicineService;
import net.therap.validator.MedicineItemValidator;
import net.therap.viewModel.InvoiceViewModel;
import net.therap.viewModel.MedicineItem;
import net.therap.viewModel.RemoveModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;
import static net.therap.controller.invoice.InvoiceController.INVOICE_CMD;
import static net.therap.model.Action.*;

/**
 * @author khandaker.maruf
 * @since 8/7/22
 */
@Controller
@RequestMapping("/invoice/medicine")
@SessionAttributes(INVOICE_CMD)
public class MedicineItemController {

    public static final String INVOICE_CMD = "invoice";
    public static final String MEDICINE_CMD = "medicineItem";
    private static final String ADD_MEDICINE_PAGE = "/invoice/addMedicine";
    private static final String REDIRECT_MEDICINE_PAGE = "redirect:/invoice/medicine";
    private static final String REDIRECT_FACILITY_PAGE = "redirect:/invoice/facility";
    private static final String REDIRECT_DOCTOR_PAGE = "redirect:/invoice/doctor";

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineEditor medicineEditor;

    @Autowired
    private MedicineItemValidator medicineItemValidator;

    @InitBinder("medicineItem")
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Medicine.class, medicineEditor);
        binder.addValidators(medicineItemValidator);
    }

    @GetMapping
    public String view(ModelMap model) {
        InvoiceViewModel invoice = (InvoiceViewModel) model.get(INVOICE_CMD);

        if(isNull(invoice) || isNull(invoice.getPatient())) {
            return REDIRECT_DOCTOR_PAGE;
        }

        setUpReferenceData(model, VIEW);

        return ADD_MEDICINE_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(MEDICINE_CMD) MedicineItem medicineItem,
                               BindingResult result,
                               @RequestParam Action action,
                               ModelMap model) {

        if(action.equals(NEXT)) {
            return REDIRECT_FACILITY_PAGE;
        }

        if(result.hasErrors()) {
            setUpReferenceData(model, SAVE);

            return ADD_MEDICINE_PAGE;
        }

        InvoiceViewModel invoice = (InvoiceViewModel) model.get(INVOICE_CMD);
        invoice.getMedicines().removeIf(facility -> facility.getMedicine().getId() == medicineItem.getMedicine().getId());
        invoice.getMedicines().add(medicineItem);

        if(action.equals(ADD)) {
            return REDIRECT_MEDICINE_PAGE;
        }

        return REDIRECT_FACILITY_PAGE;
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute("removeModel") RemoveModel removeModel, ModelMap model) {
        InvoiceViewModel invoice = (InvoiceViewModel) model.get(INVOICE_CMD);
        invoice.getMedicines().removeIf(medicineItem -> medicineItem.getMedicine().getId() == removeModel.getId());

        return REDIRECT_MEDICINE_PAGE;
    }

    private void setUpReferenceData(ModelMap model, Action action) {
        if(action.equals(VIEW)) {
            model.put(MEDICINE_CMD, new MedicineItem());
        }

        model.put("medicines", medicineService.findAll());
    }
}
