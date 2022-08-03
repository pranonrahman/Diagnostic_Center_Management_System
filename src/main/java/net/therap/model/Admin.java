package net.therap.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "admin", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})})
public class Admin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @OneToOne
    private Person person;

    public Admin() {
    }

    public Admin(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
