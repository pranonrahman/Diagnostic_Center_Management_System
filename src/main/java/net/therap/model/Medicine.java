package net.therap.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "medicine")
public class Medicine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private String genericName;

    private Double pricePerUnit;

    public Medicine() {
    }

    public Medicine(String name, String genericName, Double pricePerUnit) {
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
