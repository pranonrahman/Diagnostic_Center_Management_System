package net.therap.service;

import net.therap.dao.FacilityDao;
import net.therap.model.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class FacilityService {

    @Autowired
    private FacilityDao facilityDao;

    public List<Facility> findAll() {
        return facilityDao.findAll();
    }

    public Facility findById(long id) {
        return facilityDao.findById(id);
    }

    @Transactional
    public Facility saveOrUpdate(Facility facility) {
        return facilityDao.saveOrUpdate(facility);
    }

    @Transactional
    public void delete(Facility facility) {
        facilityDao.delete(facility);
    }
}
