package net.therap.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "doctor")
public class Doctor extends Person {

    private static final long serialVersionUID = 1L;

    private Double fee;

    @ManyToMany
    @JoinTable(
            name = "doctor_specialities",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "speciality_id")}
    )
    Set<Speciality> specialities;

    public Doctor() {
    }

    public Doctor(Long id, String name, String phone, String email, Gender gender, Date dateOfBirth,
                  Set<Role> roles, String userName, String password, Double fee, Set<Speciality> specialities) {

        super(id, name, phone, email, gender, dateOfBirth, roles, userName, password);
        this.fee = fee;
        this.specialities = specialities;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialityList) {
        this.specialities = specialityList;
    }
}
