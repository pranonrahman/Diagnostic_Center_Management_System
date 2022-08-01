package net.therap.dao;

import net.therap.model.Facility;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class FacilityDao extends Dao<Facility> {

    private static final String FIND_ALL_QUERY = "FROM Facility";

    public FacilityDao() {
        super(Facility.class);
    }

    public List<Facility> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, net.therap.model.Facility.class).getResultList();
    }
}
