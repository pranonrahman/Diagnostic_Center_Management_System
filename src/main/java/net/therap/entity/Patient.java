package net.therap.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "patient")
@Getter
@Setter
@NamedQuery(name = "Patient.findAll", query = "FROM Patient")
public class Patient extends Persistent {

    private static final long serialVersionUID = 1L;

    @OneToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private List<Prescription> prescriptions;

    @OneToMany
    private List<Invoice> invoices;

    public Patient() {
        prescriptions = new ArrayList<>();
        invoices = new ArrayList<>();
    }

    public Patient(User user) {
        this();

        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Patient)) {
            return false;
        }

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
