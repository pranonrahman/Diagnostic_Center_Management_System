package net.therap.controller;

import net.therap.model.Action;
import net.therap.model.Invoice;
import net.therap.model.Patient;
import net.therap.service.*;
import net.therap.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static net.therap.model.Action.VIEW;

/**
 * @author khandaker.maruf
 * @since 8/3/22
 */
@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private static final String VIEW_PAGE = "/invoice/view";
    private static final String INVOICE_CMD = "invoice";

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

    @GetMapping
    public String view(ModelMap modelMap) {

        setUpReferenceData(VIEW, modelMap);
        return VIEW_PAGE;
    }

    private void setUpReferenceData(Action action, ModelMap modelMap) {
        if(action.equals(VIEW)) {
            modelMap.put(INVOICE_CMD, new InvoiceViewModel());
        }

        modelMap.put("patients", patientService.findAll());
        modelMap.put("doctors", doctorService.findAll());
        modelMap.put("medicines", medicineService.findAll());
        modelMap.put("facilities", facilityService.findAll());
    }
}
