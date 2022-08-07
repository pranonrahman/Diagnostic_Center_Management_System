package net.therap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "medicine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medicine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    @Column(name = "generic_name")
    private String genericName;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "available_units")
    private int availableUnits;
}
