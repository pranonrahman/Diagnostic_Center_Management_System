package net.therap.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
public class Facility extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{name.notNull}")
    @Size(min = 1, max = 30, message = "{name.size}")
    private String name;

    @Min(value = 0, message = "{price.notNegative}")
    private double price;

    public Facility(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
