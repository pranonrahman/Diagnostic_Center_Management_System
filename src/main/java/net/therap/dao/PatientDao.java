package net.therap.dao;

import net.therap.model.Invoice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class PatientDao extends Dao<Invoice> {

    private static final String FIND_ALL_QUERY = "FROM Patient";

    public PatientDao() {
        super(Invoice.class);
    }

    public List<Invoice> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Invoice.class).getResultList();
    }
}
