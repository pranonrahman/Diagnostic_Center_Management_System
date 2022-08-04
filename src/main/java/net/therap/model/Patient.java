package net.therap.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "patient")
public class Patient extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @OneToOne
    private Person person;

    @OneToMany(fetch = FetchType.EAGER)
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
}
