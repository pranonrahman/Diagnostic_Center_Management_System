package net.therap.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "receptionist")
public class Receptionist extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @OneToOne
    private Person person;

    public Receptionist() {
    }

    public Receptionist(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
