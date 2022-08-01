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

    private static final String FIND_ALL_QUERY = "FROM Particular";

    public ParticularDao() {
        super(Particular.class);
    }

    public List<Particular> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Particular.class).getResultList();
    }
}
