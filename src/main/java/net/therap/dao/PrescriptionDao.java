package net.therap.dao;

import net.therap.model.Prescription;
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

    public List<Prescription> findAll() {
        return entityManager.createNamedQuery("Prescription.findAll", Prescription.class).getResultList();
    }
}
