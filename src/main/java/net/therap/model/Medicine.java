package net.therap.model;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Medicine extends BaseEntity {

    private String name;
    private String genericName;
    private Double pricePerUnit;

    public Medicine() {
    }

    public Medicine(Long id, String name, String genericName, Double pricePerUnit) {
        super(id);
        this.name = name;
        this.genericName = genericName;
        this.pricePerUnit = pricePerUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
