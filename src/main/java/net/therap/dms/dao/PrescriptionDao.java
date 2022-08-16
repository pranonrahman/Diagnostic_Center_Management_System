package net.therap.dms.dao;

import net.therap.dms.entity.Prescription;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class PrescriptionDao extends Dao<Prescription> {

    public PrescriptionDao() {
        super(Prescription.class);
    }

    public List<Prescription> findByDoctor(long doctorId) {
        return em.createNamedQuery("Prescription.findByDoctor", Prescription.class)
                .setParameter("doctorId", doctorId)
                .getResultList();
    }

    public List<Prescription> findAll() {
        return em.createNamedQuery("Prescription.findAll", Prescription.class)
                .getResultList();
    }
}
