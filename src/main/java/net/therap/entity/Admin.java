package net.therap.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Admin.findAll", query = "FROM Admin ORDER BY id DESC")
public class Admin extends Persistent {

    private static final long serialVersionUID = 1L;

    @OneToOne
    private User user;

    public Admin(User user) {
        this.user = user;
    }
}
