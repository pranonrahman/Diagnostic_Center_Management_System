package net.therap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "receptionist")
@Getter
@Setter
public class Receptionist extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @OneToOne
    private User user;

    public Receptionist() {
    }

    public Receptionist(User user) {
        this.user = user;
    }
}
