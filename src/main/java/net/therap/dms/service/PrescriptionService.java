package net.therap.dms.service;

import net.therap.dms.dao.PrescriptionDao;
import net.therap.dms.entity.Prescription;
import net.therap.dms.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionDao prescriptionDao;

    public Prescription findById(long id) {
        Prescription prescription = prescriptionDao.findById(id);

        if (isNull(prescription)) {
            throw new RecordNotFoundException();
        }

        return prescription;
    }

    public List<Prescription> findAll() {
        return prescriptionDao.findAll();
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
