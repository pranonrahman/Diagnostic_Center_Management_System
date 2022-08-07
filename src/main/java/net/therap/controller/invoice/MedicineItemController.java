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
            return "redirect:/invoice/doctor";
        }

        setUpReferenceData(model, VIEW);

        return ADD_MEDICINE_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(MEDICINE_CMD) MedicineItem medicineItem,
                               BindingResult result,
                               @SessionAttribute(INVOICE_CMD) InvoiceViewModel invoice,
                               @RequestParam("action") Action action,
                               ModelMap model) {

        if(action.equals(NEXT)) {
            return "redirect:/invoice/facility";
        }

        if(result.hasErrors()) {
            setUpReferenceData(model, SAVE);

            return ADD_MEDICINE_PAGE;
        }

        invoice.getMedicines().removeIf(facility -> facility.getMedicine().getId() == medicineItem.getMedicine().getId());
        invoice.getMedicines().add(medicineItem);

        if(action.equals(ADD)) {
            return "redirect:/invoice/medicine";
        }

        return "redirect:/invoice/facility";
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute("removeModel") RemoveModel removeModel,
                         ModelMap model,
                         @SessionAttribute(INVOICE_CMD) InvoiceViewModel invoice) {
        invoice.getMedicines().removeIf(medicineItem -> medicineItem.getMedicine().getId() == removeModel.getId());



        return "redirect:/invoice/medicine";
    }

    private void setUpReferenceData(ModelMap model, Action action) {
        if(action.equals(VIEW)) {
            model.put(MEDICINE_CMD, new MedicineItem());
        }

        model.put("medicines", medicineService.findAll());
    }
}
