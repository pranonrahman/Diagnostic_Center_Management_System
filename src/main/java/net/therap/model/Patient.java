package net.therap.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "patient")
public class Patient extends Person {

    private static final long serialVersionUID = 1L;

    @OneToMany
    private Set<Prescription> prescriptions;

    @OneToMany
    private Set<Invoice> invoices;

    public Patient() {
    }

    public Patient(Long id, String name, String phone, String email, Gender gender,
                   Date dateOfBirth, Set<Role> roleList, String userName, String password,
                   Set<Prescription> prescriptions, Set<Invoice> invoices) {

        super(id, name, phone, email, gender, dateOfBirth, roleList, userName, password);
        this.prescriptions = prescriptions;
        this.invoices = invoices;
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
