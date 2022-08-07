package net.therap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "id_sequence", allocationSize = 1)
    private long id;

    public boolean isNew() {
        return id == 0;
    }
}
