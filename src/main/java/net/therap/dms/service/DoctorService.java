package net.therap.dms.service;

import net.therap.dms.dao.DoctorDao;
import net.therap.dms.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class DoctorService {

    @Autowired
    private DoctorDao doctorDao;

    public Doctor findById(long id) {
        return doctorDao.findById(id);
    }

    public List<Doctor> findAll() {
        return doctorDao.findAll();
    }

    @Transactional
    public Doctor saveOrUpdate(Doctor doctor) {
        return doctorDao.saveOrUpdate(doctor);
    }

    @Transactional
    public void delete(Doctor doctor) {
        doctorDao.delete(doctor);
    }
}
