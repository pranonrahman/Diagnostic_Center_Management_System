package net.therap.service;

import net.therap.dao.PatientDao;
import net.therap.model.Invoice;
import net.therap.model.Patient;
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

    public List<Patient> findAll() {
        return patientDao.findAll();
    }

    public Patient findById(Long id) {
        return patientDao.findById(id);
    }

    @Transactional
    public Patient saveOrUpdate(Patient patient) {
        if(patient.isNew()) {
            patient = patientDao.save(patient);
        } else {
            patient = patientDao.update(patient);
        }

        return patient;
    }

    @Transactional
    public void delete(Patient patient) {
        patientDao.delete(patient);
    }
}
