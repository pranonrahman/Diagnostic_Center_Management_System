package net.therap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "invoice")
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Invoice.findAll", query = "FROM Invoice"),
        @NamedQuery(name = "Invoice.findByPatientId", query = "FROM Invoice where patient.id = :patientId"),
})
public class Invoice extends Persistent {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "invoice_id")
    private String invoiceId;

    @NotNull
    @Column(name = "generation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date generationDate;

    @Column(name = "total_cost")
    private double totalCost;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "receptionist_id",
            nullable = false
    )
    private User generatedBy;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "patient_id",
            nullable = false
    )
    private Patient patient;

    @ManyToMany
    @JoinTable(
            name = "invoice_particular",
            joinColumns = {@JoinColumn(name = "invoice_id")},
            inverseJoinColumns = {@JoinColumn(name = "particular_id")}
    )
    private List<Particular> particulars;

    public Invoice() {
        particulars = new ArrayList<>();
        invoiceId = UUID.randomUUID().toString();
        generationDate = new Date();
    }
}
