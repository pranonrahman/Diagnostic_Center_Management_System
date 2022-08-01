package net.therap.dao;

import net.therap.model.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class AdminDao extends Dao<Admin> {

    private static final String FIND_ALL_QUERY = "FROM Admin";

    public AdminDao() {
        super(Admin.class);
    }

    public List<Admin> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Admin.class).getResultList();
    }
}
