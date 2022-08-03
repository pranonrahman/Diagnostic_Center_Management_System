package net.therap.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "prescription")
public class Prescription extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String symptoms;

    private String diagnosis;

    private String comment;

    private Date dateOfVisit;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToMany
    private Set<Facility> facilities;

    @ManyToMany
    private Set<Medicine> medicines;

    public Prescription() {
        facilities = new HashSet<>();
    }

    public Prescription(String symptoms, String diagnosis, String comment, Date dateOfVisit, Invoice invoice,
                        Doctor doctor) {

        this();

        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.comment = comment;
        this.dateOfVisit = dateOfVisit;
        this.invoice = invoice;
        this.doctor = doctor;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(Date dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Set<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }
}
