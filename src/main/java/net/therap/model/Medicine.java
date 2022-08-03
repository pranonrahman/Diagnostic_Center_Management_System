package net.therap.model;

import javax.persistence.Column;
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

    @Column(name = "generic_name")
    private String genericName;

    @Column(name = "unit_price")
    private Double unitPrice;

    public Medicine() {
    }

    public Medicine(String name, String genericName, Double unitPrice) {
        this.name = name;
        this.genericName = genericName;
        this.unitPrice = unitPrice;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double pricePerUnit) {
        this.unitPrice = pricePerUnit;
    }
}
