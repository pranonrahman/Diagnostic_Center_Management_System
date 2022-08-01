package net.therap.model;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Particular extends BaseEntity {

    private String name;
    private Double cost;
    private Integer units;

    public Particular() {
    }

    public Particular(Long id, String name, Double cost, Integer units) {
        super(id);
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
