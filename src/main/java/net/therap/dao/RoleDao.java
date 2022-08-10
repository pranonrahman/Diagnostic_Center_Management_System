package net.therap.dao;

import net.therap.model.Role;
import net.therap.model.RoleEnum;
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
        return entityManager.createNamedQuery("Role.findAll", Role.class).getResultList();
    }

    public Role findByRole(RoleEnum role) {
        try {
            return entityManager.createNamedQuery("Role.findByName", Role.class)
                    .setParameter("role", role)
                    .getSingleResult();

        } catch (NoResultException noResultException) {
            return null;
        }
    }
}
