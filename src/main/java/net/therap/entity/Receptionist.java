package net.therap.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
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
@NoArgsConstructor
@NamedQuery(name = "Receptionist.findAll", query = "FROM Receptionist ORDER BY id DESC")
public class Receptionist extends Persistent {

    private static final long serialVersionUID = 1L;

    @OneToOne
    private User user;

    public Receptionist(User user) {
        this.user = user;
    }
}
