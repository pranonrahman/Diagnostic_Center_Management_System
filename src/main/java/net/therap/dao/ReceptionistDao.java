package net.therap.dao;

import net.therap.model.Receptionist;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class ReceptionistDao extends Dao<Receptionist> {

    private static final String FIND_ALL_QUERY = "FROM Receptionist";

    public ReceptionistDao() {
        super(Receptionist.class);
    }

    public List<Receptionist> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Receptionist.class).getResultList();
    }
}
