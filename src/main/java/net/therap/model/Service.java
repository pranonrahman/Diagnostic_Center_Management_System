package net.therap.model;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Service extends BaseEntity {

    private String name;
    private Double price;

    public Service() {
    }

    public Service(Long id, String name, Double price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
