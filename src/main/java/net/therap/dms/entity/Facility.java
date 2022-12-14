package net.therap.dms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "facility")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Facility.findAll", query = "FROM Facility ORDER BY id DESC")
public class Facility extends Persistent {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{name.notNull}")
    @Size(min = 1, max = 30, message = "{name.size}")
    private String name;

    @Min(value = 0, message = "{price.notNegative}")
    private double price;

    public Facility(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
