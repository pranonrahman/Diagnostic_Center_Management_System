package net.therap.command;

import lombok.Getter;
import lombok.Setter;
import net.therap.entity.Doctor;
import net.therap.entity.Patient;
import net.therap.entity.Receptionist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author khandaker.maruf
 * @since 8/3/22
 */
@Getter
@Setter
public class InvoiceCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private Receptionist receptionist;
    private long invoiceId;
    private double totalCost;
    private Patient patient;
    private List<Doctor> doctors;
    private List<MedicineItemCmd> medicines;
    private List<FacilityItemCmd> facilities;

    public InvoiceCmd() {
        doctors = new ArrayList<>();
        medicines = new ArrayList<>();
        facilities = new ArrayList<>();
    }
}
