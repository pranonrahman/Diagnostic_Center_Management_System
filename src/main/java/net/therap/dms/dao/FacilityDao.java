package net.therap.dms.dao;

import net.therap.dms.entity.Facility;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class FacilityDao extends Dao<Facility> {

    public FacilityDao() {
        super(Facility.class);
    }

    public List<Facility> findAll() {
        return em.createNamedQuery("Facility.findAll", Facility.class)
                .getResultList();
    }
}
