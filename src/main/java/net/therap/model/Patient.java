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
@Table(name = "patient")
@Getter
@Setter
public class Patient extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @OneToOne
    private Person person;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<Prescription> prescriptions;

    @OneToMany
    private Set<Invoice> invoices;

    public Patient() {
        prescriptions = new HashSet<>();
        invoices = new HashSet<>();
    }

    public Patient(Person person) {
        this();

        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
