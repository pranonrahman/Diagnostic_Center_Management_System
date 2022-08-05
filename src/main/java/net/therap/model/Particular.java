package net.therap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Particular extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    @Column(name = "unit_price")
    private Double unitPrice;

    private Integer units;
}

