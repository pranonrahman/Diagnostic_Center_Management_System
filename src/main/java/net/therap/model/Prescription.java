package net.therap.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToMany
    private List<Facility> facilityList;

    @ManyToMany
    private List<Medicine> medicineList;

    public Prescription() {
    }

    public Prescription(Long id, String symptoms, String diagnosis, String comment, Date dateOfVisit, Patient patient,
                        Doctor doctor, List<Facility> facilityList, List<Medicine> medicineList) {

        super(id);
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.comment = comment;
        this.dateOfVisit = dateOfVisit;
        this.patient = patient;
        this.doctor = doctor;
        this.facilityList = facilityList;
        this.medicineList = medicineList;
    }
}
