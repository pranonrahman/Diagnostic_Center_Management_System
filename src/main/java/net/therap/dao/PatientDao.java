package net.therap.dao;

import net.therap.model.Invoice;
import net.therap.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class PatientDao extends Dao<Patient> {

    private static final String FIND_ALL_QUERY = "FROM Patient";

    public PatientDao() {
        super(Patient.class);
    }

    public List<Patient> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Patient.class).getResultList();
    }
}
