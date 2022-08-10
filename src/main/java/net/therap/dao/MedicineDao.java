package net.therap.dao;

import net.therap.model.Medicine;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class MedicineDao extends Dao<Medicine> {

    public MedicineDao() {
        super(Medicine.class);
    }

    public List<Medicine> findAll() {
        return entityManager.createNamedQuery("Medicine.findAll", Medicine.class).getResultList();
    }
}
