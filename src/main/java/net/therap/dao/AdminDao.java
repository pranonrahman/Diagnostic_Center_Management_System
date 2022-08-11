package net.therap.dao;

import net.therap.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class AdminDao extends Dao<Admin> {

    public AdminDao() {
        super(Admin.class);
    }

    public List<Admin> findAll() {
        return em.createNamedQuery("Admin.findAll", Admin.class).getResultList();
    }
}
