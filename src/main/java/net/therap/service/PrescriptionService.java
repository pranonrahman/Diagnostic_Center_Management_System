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

    public Prescription findById(Long id) {
        return prescriptionDao.findById(id);
    }

    @Transactional
    public Prescription saveOrUpdate(Prescription prescription) {
        if(prescription.isNew()) {
            prescription = prescriptionDao.save(prescription);
        } else {
            prescription = prescriptionDao.update(prescription);
        }

        return prescription;
    }

    @Transactional
    public void delete(Prescription prescription) {
        prescriptionDao.delete(prescription);
    }
}
