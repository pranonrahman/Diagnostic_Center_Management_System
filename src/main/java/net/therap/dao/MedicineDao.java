package net.therap.dao;

import net.therap.model.Medicine;
import net.therap.model.Speciality;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class MedicineDao extends Dao<Medicine> {

    private static final String FIND_ALL_QUERY = "FROM Speciality";

    public MedicineDao() {
        super(Medicine.class);
    }

    public List<Medicine> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Medicine.class).getResultList();
    }
}
