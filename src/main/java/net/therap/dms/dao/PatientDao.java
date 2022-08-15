package net.therap.dms.dao;

import net.therap.dms.entity.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class PatientDao extends Dao<Patient> {

    public PatientDao() {
        super(Patient.class);
    }

    public List<Patient> findAll() {
        return em.createNamedQuery("Patient.findAll", Patient.class)
                .getResultList();
    }
}
