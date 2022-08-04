package net.therap.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private RoleEnum name;

    public Role() {
    }

    public Role(RoleEnum name) {
        this.name = name;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}
