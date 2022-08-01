package net.therap.model;

import java.util.Date;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Receptionist extends Person {

    public Receptionist() {
    }

    public Receptionist(Long id, String name, String phone, String email, Gender gender, Date dateOfBirth, List<Role> roleList, String userName, String password) {
        super(id, name, phone, email, gender, dateOfBirth, roleList, userName, password);
    }
}
