package net.therap.dao;

import net.therap.entity.Role;
import net.therap.entity.RoleEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class RoleDao extends Dao<Role> {

    public RoleDao() {
        super(Role.class);
    }

    public List<Role> findAll() {
        return em.createNamedQuery("Role.findAll", Role.class).getResultList();
    }

    public Role findByName(RoleEnum role) {
        try {
            return em.createNamedQuery("Role.findByName", Role.class)
                    .setParameter("role", role)
                    .getSingleResult();

        } catch (NoResultException noResultException) {
            return null;
        }
    }
}
