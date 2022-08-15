package net.therap.dms.dao;

import net.therap.dms.entity.Role;
import net.therap.dms.entity.RoleEnum;
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

    public Role findByName(RoleEnum role) {
        try {
            return em.createNamedQuery("Role.findByName", Role.class)
                    .setParameter("role", role)
                    .getSingleResult();

        } catch (NoResultException noResultException) {
            return null;
        }
    }

    public List<Role> findAll() {
        return em.createNamedQuery("Role.findAll", Role.class)
                .getResultList();
    }
}
