package net.therap.service;

import net.therap.dao.SpecialityDao;
import net.therap.model.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class SpecialityService {

    @Autowired
    private SpecialityDao specialityDao;

    public List<Speciality> findAll() {
        return specialityDao.findAll();
    }

    public Speciality findById(Long id) {
        return specialityDao.findById(id);
    }

    @Transactional
    public Speciality saveOrUpdate(Speciality speciality) {
        if(speciality.isNew()) {
            speciality = specialityDao.save(speciality);
        } else {
            speciality = specialityDao.update(speciality);
        }

        return speciality;
    }

    @Transactional
    public void delete(Speciality speciality) {
        specialityDao.delete(speciality);
    }
}
