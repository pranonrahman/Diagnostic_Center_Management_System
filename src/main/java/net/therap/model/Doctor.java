package net.therap.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "doctor")
public class Doctor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToMany
    @JoinTable(
            name = "doctor_specialities",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "speciality_id")}
    )
    Set<Speciality> specialities;

    private Double fee;

    @OneToOne
    private Person person;

    @OneToMany
    private Set<Prescription> prescriptions;

    public Doctor() {
        prescriptions = new HashSet<>();
        specialities = new HashSet<>();
    }

    public Doctor(Double fee, Person person) {
        this();

        this.fee = fee;
        this.person = person;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
