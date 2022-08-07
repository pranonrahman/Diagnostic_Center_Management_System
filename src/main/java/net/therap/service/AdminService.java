package net.therap.service;

import net.therap.dao.AdminDao;
import net.therap.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    public Admin findById(long id) {
        return adminDao.findById(id);
    }

    @Transactional
    public Admin saveOrUpdate(Admin admin) {
        return adminDao.saveOrUpdate(admin);
    }

    @Transactional
    public void delete(Admin admin) {
        adminDao.delete(admin);
    }
}
