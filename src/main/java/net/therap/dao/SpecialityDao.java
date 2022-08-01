package net.therap.dao;

import net.therap.model.Speciality;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class SpecialityDao extends Dao<Speciality> {

    private static final String FIND_ALL_QUERY = "FROM Speciality";

    public SpecialityDao() {
        super(Speciality.class);
    }

    public List<Speciality> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Speciality.class).getResultList();
    }
}
