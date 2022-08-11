package net.therap.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Role.findAll", query = "FROM Role"),
        @NamedQuery(name = "Role.findByName", query = "FROM Role WHERE name = :role"),
})
public class Role extends Persistent {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    public Role(RoleEnum name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;

        return getName() == role.getName();
    }
}
