package net.therap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "doctor")
@Getter
@Setter
public class Doctor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private double fee;

    @OneToOne
    private Person person;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor")
    private Set<Prescription> prescriptions;

    public Doctor() {
        prescriptions = new HashSet<>();
    }

    public Doctor(Double fee, Person person) {
        this();

        this.fee = fee;
        this.person = person;
    }
}
