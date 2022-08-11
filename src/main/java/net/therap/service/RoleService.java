package net.therap.service;

import net.therap.dao.RoleDao;
import net.therap.entity.Role;
import net.therap.entity.RoleEnum;
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

    public Role findById(long id) {
        return roleDao.findById(id);
    }

    public Role findByName(RoleEnum role) {
        return roleDao.findByName(role);
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
