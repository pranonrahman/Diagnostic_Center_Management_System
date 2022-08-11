package net.therap.service;

import net.therap.dao.PatientDao;
import net.therap.entity.Patient;
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

    public Patient findById(long id) {
        return patientDao.findById(id);
    }

    public List<Patient> findAll() {
        return patientDao.findAll();
    }

    @Transactional
    public Patient saveOrUpdate(Patient patient) {
        return patientDao.saveOrUpdate(patient);
    }

    @Transactional
    public void delete(Patient patient) {
        patientDao.delete(patient);
    }
}
