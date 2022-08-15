package net.therap.dms.service;

import net.therap.dms.dao.PatientDao;
import net.therap.dms.entity.Patient;
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
public class PatientService {

    @Autowired
    private PatientDao patientDao;

    public Patient findById(long id) {
        Patient patient = patientDao.findById(id);

        if (isNull(patient)) {
            throw new RecordNotFoundException();
        }

        return patient;
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
