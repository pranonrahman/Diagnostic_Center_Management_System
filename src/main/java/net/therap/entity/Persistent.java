package net.therap.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@MappedSuperclass
@Getter
@Setter
public class Persistent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "id_sequence", allocationSize = 1)
    private long id;

    public boolean isNew() {
        return getId() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Persistent)) {
            return false;
        }

        Persistent that = (Persistent) o;
        
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
