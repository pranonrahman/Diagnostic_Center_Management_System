package net.therap.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "admin", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})})
public class Admin extends Person {

    private static final long serialVersionUID = 1L;

    public Admin() {
        super();
    }

    public Admin(Long id, String name, String phone, String email, Gender gender, Date dateOfBirth,
                 Set<Role> roles, String userName, String password) {

        super(id, name, phone, email, gender, dateOfBirth, roles, userName, password);
    }
}
