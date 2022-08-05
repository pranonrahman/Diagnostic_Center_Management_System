package net.therap.controller;

import net.therap.editor.*;
import net.therap.model.*;
import net.therap.service.*;
import net.therap.viewModel.FacilityItem;
import net.therap.viewModel.InvoiceViewModel;
import net.therap.viewModel.MedicineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static net.therap.controller.InvoiceController.INVOICE_CMD;
import static net.therap.model.Action.ADD;

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
    private static final String ADD_DOCTOR_PAGE = "/invoice/addDoctor";
    private static final String ADD_MEDICINE_PAGE = "/invoice/addMedicine";
    private static final String ADD_FACILITY_PAGE = "/invoice/addFacility";
    private static final String FORM_PAGE = "/invoice/form";
    public static final String INVOICE_CMD = "invoice";
    public static final String MEDICINE_CMD = "medicineItem";
    public static final String FACILITY_CMD = "facilityItem";

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private PatientEditor patientEditor;

    @Autowired
    private DoctorEditor doctorEditor;

    @Autowired
    private MedicineEditor medicineEditor;

    @Autowired
    private FacilityEditor facilityEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Patient.class, patientEditor);
        binder.registerCustomEditor(Doctor.class, doctorEditor);
        binder.registerCustomEditor(Medicine.class, medicineEditor);
        binder.registerCustomEditor(Facility.class, facilityEditor);
    }

    @GetMapping("/doctor")
    public String viewDoctor(ModelMap modelMap) {

        modelMap.put("patients", patientService.findAll());
        modelMap.put("doctors", doctorService.findAll());
        modelMap.put(INVOICE_CMD, new InvoiceViewModel());
        return ADD_DOCTOR_PAGE;
    }

    @PostMapping("/doctor")
    public String saveDoctor(@Valid @ModelAttribute(INVOICE_CMD) InvoiceViewModel invoice,
                          BindingResult result,
                          ModelMap modelMap) {

//        if(result.hasErrors()) {
//            return FORM_ONE_PAGE;
//        }

        return "redirect:/invoice/medicine";
    }

    @GetMapping("/medicine")
    public String viewTwo(ModelMap modelMap) {

        modelMap.put("medicines", medicineService.findAll());
        modelMap.put(MEDICINE_CMD, new MedicineItem());

        return ADD_MEDICINE_PAGE;
    }

    @PostMapping("/medicine")
    public String saveMedicine(@Valid @ModelAttribute(MEDICINE_CMD) MedicineItem medicineItem,
                          BindingResult result,
                          @SessionAttribute(INVOICE_CMD) InvoiceViewModel invoice,
                          @RequestParam("action") Action action,
                          ModelMap modelMap) {

//        if(result.hasErrors()) {
//            return FORM_ONE_PAGE;
//        }
        invoice.getMedicines().add(medicineItem);

        if(action.equals(ADD)) {
            return "redirect:/invoice/medicine";
        }

        return "redirect:/invoice/facility";
    }

    @GetMapping("/facility")
    public String viewFacility(ModelMap modelMap) {
        modelMap.put("facilities", facilityService.findAll());
        modelMap.put(FACILITY_CMD, new FacilityItem());

        return ADD_FACILITY_PAGE;
    }

    @PostMapping("/facility")
    public String saveFacility(@Valid @ModelAttribute(FACILITY_CMD) FacilityItem facilityItem,
                          BindingResult result,
                          @SessionAttribute(INVOICE_CMD) InvoiceViewModel invoice,
                          @RequestParam("action") Action action,
                          RedirectAttributes ra,
                          ModelMap modelMap) {

//        if(result.hasErrors()) {
//            return FORM_ONE_PAGE;
//        }
        invoice.getFacilities().add(facilityItem);

        if(action.equals(ADD)) {
            return "redirect:/invoice/facility";
        }

        Invoice createdInvoice = invoiceService.saveOrUpdate(invoice);

        ra.addAttribute("id", createdInvoice.getId());
        return "redirect:/invoice/view";
    }

    @GetMapping("/view")
    public String view(@RequestParam long id, ModelMap modelMap) {
        modelMap.addAttribute(INVOICE_CMD, invoiceService.findById(id));

        return VIEW_PAGE;
    }

    @GetMapping("/list")
    public String list(ModelMap modelMap) {
        modelMap.addAttribute("invoices", invoiceService.findAll());

        return LIST_VIEW_PAGE;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute(INVOICE_CMD) InvoiceViewModel invoice,
                       BindingResult result,
                       ModelMap modelMap) {

        if(result.hasErrors()) {
            return FORM_PAGE;
        }

        invoiceService.saveOrUpdate(invoice);

        return VIEW_PAGE;
    }

    private void setUpReferenceData(Action action, ModelMap modelMap) {
//        if(action.equals(VIEW) && !modelMap.containsAttribute(INVOICE_CMD)){
//            m.addAttribute(HUMAN_CMD, new Human());
//        }

//        modelMap.put("patients", getCheckOutItem(patientService.findAll()));
//        modelMap.put("doctors", getCheckOutItem(doctorService.findAll()));
//        modelMap.put("medicines", getCheckOutItem(medicineService.findAll()));
//        modelMap.put("facilities", getCheckOutItem(facilityService.findAll()));
    }
}
