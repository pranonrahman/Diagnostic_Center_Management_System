package net.therap.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private RoleEnum name;

    public Role(RoleEnum name) {
        this.name = name;
    }
}
