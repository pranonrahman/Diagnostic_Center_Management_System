package net.therap.dms.command;

import lombok.Getter;
import lombok.Setter;
import net.therap.dms.entity.Doctor;
import net.therap.dms.entity.Patient;
import net.therap.dms.entity.Receptionist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Doctor> doctors;
    private List<MedicineItemCmd> medicines;
    private List<FacilityItemCmd> facilities;

    public InvoiceCmd() {
        doctors = new HashSet<>();
        medicines = new ArrayList<>();
        facilities = new ArrayList<>();
    }
}
