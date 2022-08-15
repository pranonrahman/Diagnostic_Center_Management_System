package net.therap.dms.dao;

import net.therap.dms.entity.Doctor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class DoctorDao extends Dao<Doctor> {

    public DoctorDao() {
        super(Doctor.class);
    }

    public List<Doctor> findAll() {
        return em.createNamedQuery("Doctor.findAll", Doctor.class)
                .getResultList();
    }
}
