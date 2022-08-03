package net.therap.service;

import net.therap.dao.RoleDao;
import net.therap.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Transactional
    public Role saveOrUpdate(Role role) {
        return roleDao.saveOrUpdate(role);
    }

    @Transactional
    public void delete(Role role) {
        roleDao.delete(role);
    }
}
