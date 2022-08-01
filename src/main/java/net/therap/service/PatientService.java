package net.therap.service;

import net.therap.dao.PatientDao;
import net.therap.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class PatientService {

    @Autowired
    private PatientDao patientDao;

    public List<Invoice> findAll() {
        return patientDao.findAll();
    }

    public Invoice findById(Long id) {
        return patientDao.findById(id);
    }

    @Transactional
    public Invoice saveOrUpdate(Invoice invoice) {
        if(invoice.isNew()) {
            invoice = patientDao.save(invoice);
        } else {
            invoice = patientDao.update(invoice);
        }

        return invoice;
    }

    @Transactional
    public void delete(Invoice invoice) {
        patientDao.delete(invoice);
    }
}
