package net.therap.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "receptionist")
public class Receptionist extends Person {

    private static final long serialVersionUID = 1L;

    public Receptionist() {
    }

    public Receptionist(Long id, String name, String phone, String email, Gender gender, Date dateOfBirth,
                        Set<Role> roleSet, String userName, String password) {

        super(id, name, phone, email, gender, dateOfBirth, roleSet, userName, password);
    }
}
