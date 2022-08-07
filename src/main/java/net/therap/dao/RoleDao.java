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

    private static final String FIND_ALL_QUERY = "FROM Role";
    private static final String FIND_BY_ROLE_QUERY = "FROM Role WHERE name = :role";

    public RoleDao() {
        super(Role.class);
    }

    public List<Role> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Role.class).getResultList();
    }

    public Role findByRole(RoleEnum role) {
        try {
            return entityManager.createQuery(FIND_BY_ROLE_QUERY, Role.class)
                    .setParameter("role", role)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }
}
