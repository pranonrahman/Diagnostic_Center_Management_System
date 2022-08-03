package net.therap.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "id_sequence", allocationSize = 1)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return Objects.isNull(id) || id == 0;
    }
}
