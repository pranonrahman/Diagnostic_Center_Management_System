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

    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoiceIdGenerator")
    @SequenceGenerator(name = "invoiceIdGenerator", sequenceName = "invoice_id_gen", initialValue = 1000, allocationSize = 1)
    private Long invoiceId;

    @Column(name = "total_cost")
    private Double totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "receptionist_id",
            nullable = false
    )
    private Receptionist generatedBy;

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

    public Invoice(Long invoiceId, Double totalCost, Receptionist generatedBy, Patient patient) {
        this.invoiceId = invoiceId;
        this.totalCost = totalCost;
        this.generatedBy = generatedBy;
        this.patient = patient;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Receptionist getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(Receptionist generator) {
        this.generatedBy = generator;
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
