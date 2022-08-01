package net.therap.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "speciality")
public class Speciality extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    public Speciality() {
    }

    public Speciality(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
