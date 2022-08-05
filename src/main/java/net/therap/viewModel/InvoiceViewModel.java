package net.therap.viewModel;

import lombok.Getter;
import lombok.Setter;
import net.therap.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khandaker.maruf
 * @since 8/3/22
 */
@Getter
@Setter
public class InvoiceViewModel {

    private long id;
    private Receptionist generatedBy;
    private long invoiceId;
    private Double totalCost;
    private Patient patient;
    private List<Doctor> doctors;
    private List<MedicineItem> medicines;
    private List<FacilityItem> facilities;

    public InvoiceViewModel() {
        doctors = new ArrayList<>();
        medicines = new ArrayList<>();
        facilities = new ArrayList<>();
    }
}
