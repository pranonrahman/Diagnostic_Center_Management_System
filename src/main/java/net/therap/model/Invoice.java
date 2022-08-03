package net.therap.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "invoice")
public class Invoice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "total_cost")
    private Double totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "receptionist_id",
            nullable = false
    )
    private Receptionist generator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "patient_id",
            nullable = false
    )
    private Patient patient;

    @ManyToMany
    @JoinTable(
            name = "invoice_particular_join_table",
            joinColumns = {@JoinColumn(name = "invoice_id")},
            inverseJoinColumns = {@JoinColumn(name = "particular_id")}
    )
    private Set<Particular> particulars;

    public Invoice() {
        particulars = new HashSet<>();
    }

    public Invoice(Double totalCost, Receptionist generator, Patient patient) {
        this.totalCost = totalCost;
        this.generator = generator;
        this.patient = patient;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Receptionist getGenerator() {
        return generator;
    }

    public void setGenerator(Receptionist generator) {
        this.generator = generator;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<Particular> getParticulars() {
        return particulars;
    }

    public void setParticulars(Set<Particular> particulars) {
        this.particulars = particulars;
    }
}
