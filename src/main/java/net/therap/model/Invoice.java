package net.therap.model;

import javax.persistence.*;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "invoice")
public class Invoice extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
    private Invoice invoice;

    @ManyToMany
    @JoinTable(
            name = "invoice_particular_join_table",
            joinColumns = {@JoinColumn(name = "invoice_id")},
            inverseJoinColumns = {@JoinColumn(name = "particular_id")}
    )
    private Set<Particular> particulars;

    public Invoice() {
    }

    public Invoice(Long id, Set<Particular> particulars,
                   Double totalCost, Receptionist generator, Invoice invoice) {
        super(id);
        this.particulars = particulars;
        this.totalCost = totalCost;
        this.generator = generator;
        this.invoice = invoice;
    }

    public Set<Particular> getParticulars() {
        return particulars;
    }

    public void setParticulars(Set<Particular> particularList) {
        this.particulars = particularList;
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

    public Invoice getPatient() {
        return invoice;
    }

    public void setPatient(Invoice invoice) {
        this.invoice = invoice;
    }
}
