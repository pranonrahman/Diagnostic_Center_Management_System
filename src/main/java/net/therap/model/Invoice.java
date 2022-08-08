package net.therap.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "invoice")
@Getter
@Setter
public class Invoice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoiceIdGenerator")
    @SequenceGenerator(name = "invoiceIdGenerator", sequenceName = "invoice_id_gen", initialValue = 1000, allocationSize = 1)
    private long invoiceId;

    @Column(name = "generation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date generationDate = new Date();

    @Column(name = "total_cost")
    private Double totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "receptionist_id",
            nullable = false
    )
    private Person generatedBy;

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

    public Invoice(long invoiceId, Double totalCost, Person person, Patient patient) {
        this.invoiceId = invoiceId;
        this.totalCost = totalCost;
        this.generatedBy = generatedBy;
        this.patient = patient;
    }
}
