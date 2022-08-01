package net.therap.model;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Invoice extends BaseEntity {

    private List<Particular> particularList;
    private Double totalCost;
    private Receptionist generator;
    private Patient patient;

    public Invoice() {
    }

    public Invoice(Long id, List<Particular> particularList,
                   Double totalCost, Receptionist generator, Patient patient) {
        super(id);
        this.particularList = particularList;
        this.totalCost = totalCost;
        this.generator = generator;
        this.patient = patient;
    }

    public List<Particular> getParticularList() {
        return particularList;
    }

    public void setParticularList(List<Particular> particularList) {
        this.particularList = particularList;
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
}
