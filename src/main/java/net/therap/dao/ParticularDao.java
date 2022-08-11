package net.therap.dao;

import net.therap.model.Particular;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class ParticularDao extends Dao<Particular> {

    public ParticularDao() {
        super(Particular.class);
    }

    public List<Particular> findAll() {
        return em.createNamedQuery("Particular.findAll", Particular.class).getResultList();
    }
}
