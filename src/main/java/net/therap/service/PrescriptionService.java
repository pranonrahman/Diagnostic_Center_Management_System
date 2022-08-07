package net.therap.service;

import net.therap.dao.PrescriptionDao;
import net.therap.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionDao prescriptionDao;

    public List<Prescription> findAll() {
        return prescriptionDao.findAll();
    }

    public Prescription findById(long id) {
        return prescriptionDao.findById(id);
    }

    @Transactional
    public Prescription saveOrUpdate(Prescription prescription) {
        return prescriptionDao.saveOrUpdate(prescription);
    }

    @Transactional
    public void delete(Prescription prescription) {
        prescriptionDao.delete(prescription);
    }
}
