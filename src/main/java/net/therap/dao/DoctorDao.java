package net.therap.dao;

import net.therap.model.Doctor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class DoctorDao extends Dao<Doctor> {

    private static final String FIND_ALL_QUERY = "FROM Doctor";

    public DoctorDao() {
        super(Doctor.class);
    }

    public List<Doctor> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Doctor.class).getResultList();
    }
}
