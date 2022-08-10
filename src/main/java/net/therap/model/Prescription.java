package net.therap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(max = 3000, message = "{size.max}")
    private String symptoms;

    @Size(max = 3000, message = "{size.max}")
    private String diagnosis;

    @Size(max = 3000, message = "{size.max}")
    private String comment;

    @Column(name = "date_of_visit")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfVisit;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToMany
    @JoinTable(
            name = "prescription_facility",
            joinColumns = {@JoinColumn(name = "prescription_id")},
            inverseJoinColumns = {@JoinColumn(name = "facility_id")}
    )
    private Set<Facility> facilities;

    @Size(max = 3000, message = "{size.max}")
    private String medicines;

    public Prescription() {
        facilities = new HashSet<>();
    }
}
