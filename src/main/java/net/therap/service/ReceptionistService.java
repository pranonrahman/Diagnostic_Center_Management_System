package net.therap.service;

import net.therap.dao.ReceptionistDao;
import net.therap.entity.Receptionist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class ReceptionistService {

    @Autowired
    private ReceptionistDao receptionistDao;

    public List<Receptionist> findAll() {
        return receptionistDao.findAll();
    }

    public Receptionist findById(long id) {
        return receptionistDao.findById(id);
    }

    @Transactional
    public Receptionist saveOrUpdate(Receptionist receptionist) {
        return receptionistDao.saveOrUpdate(receptionist);
    }

    @Transactional
    public void delete(Receptionist receptionist) {
        receptionistDao.delete(receptionist);
    }
}
