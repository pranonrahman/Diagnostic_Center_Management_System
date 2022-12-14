package net.therap.dms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@NamedQuery(name = "Medicine.findAll", query = "FROM Medicine  ORDER BY id DESC")
public class Medicine extends Persistent {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{name.notNull}")
    @Size(min = 1, max = 30, message = "{name.size}")
    private String name;

    @NotNull(message = "{name.notNull}")
    @Size(min = 1, max = 30, message = "{name.size}")
    @Column(name = "generic_name")
    private String genericName;

    @Min(value = 0, message = "{unitPrice.notNegative}")
    @Column(name = "unit_price")
    private double unitPrice;

    @Min(value = 0, message = "{availableUnits.notNegative}")
    @Column(name = "available_units")
    private int availableUnits;
}
