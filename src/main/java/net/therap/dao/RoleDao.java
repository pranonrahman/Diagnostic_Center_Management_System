package net.therap.dao;

import net.therap.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class RoleDao extends Dao<Role> {

    private static final String FIND_ALL_QUERY = "FROM Role";

    public RoleDao() {
        super(Role.class);
    }

    public List<Role> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Role.class).getResultList();
    }
}
