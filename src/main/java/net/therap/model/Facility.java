package net.therap.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

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

    private String name;

    private double price;

    public Facility(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
