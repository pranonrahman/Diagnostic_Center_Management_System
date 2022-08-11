package net.therap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Particular.findAll", query = "FROM Particular")
public class Particular extends Persistent {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{name.notNull}")
    @Size(min = 1, max = 30, message = "{name.size}")
    private String name;

    @Min(value = 0, message = "{unitPrice.notNegative}")
    @Column(name = "unit_price")
    private double unitPrice;

    @Min(value = 0, message = "{units.notNegative}")
    private int units;
}

