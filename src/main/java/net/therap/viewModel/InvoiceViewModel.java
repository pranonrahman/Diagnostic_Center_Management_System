package net.therap.viewModel;

import net.therap.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khandaker.maruf
 * @since 8/3/22
 */
public class InvoiceViewModel {

    private long id;
    private Receptionist generatedBy;
    private long invoiceId;
    private Double totalCost;
    private Patient patient;
    private List<Doctor> doctors;
    private List<Medicine> medicines;
    private List<Facility> facilities;

    public InvoiceViewModel() {
        doctors = new ArrayList<>();
        medicines = new ArrayList<>();
        facilities = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Receptionist getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(Receptionist generatedBy) {
        this.generatedBy = generatedBy;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }
}
