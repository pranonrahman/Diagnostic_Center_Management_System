package net.therap.model;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class Prescription extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String symptoms;

    private String diagnosis;

    private String comment;

    @Column(name = "date_of_visit")
    private Date dateOfVisit;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToMany
    private Set<Facility> facilities;

    private String medicines;

    public Prescription() {
        facilities = new HashSet<>();
    }

    public Prescription(String symptoms, String diagnosis, String comment, Date dateOfVisit, Patient patient,
                        Doctor doctor) {

        this();

        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.comment = comment;
        this.dateOfVisit = dateOfVisit;
        this.patient = patient;
        this.doctor = doctor;
    }
}
