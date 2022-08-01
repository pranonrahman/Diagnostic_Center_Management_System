package net.therap.model;

import javax.persistence.*;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String name;

    public Role() {
    }

    public Role(Long id, String name) {
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
