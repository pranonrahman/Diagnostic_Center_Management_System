package net.therap.model;

import javax.persistence.Entity;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
public class Particular extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private Double cost;

    private Integer units;

    public Particular() {
    }

    public Particular(String name, Double cost, Integer units) {
        this.name = name;
        this.cost = cost;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }
}
