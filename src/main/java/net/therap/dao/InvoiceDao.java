package net.therap.dao;

import net.therap.model.Invoice;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Repository
public class InvoiceDao extends Dao<Invoice> {

    private static final String FIND_ALL_QUERY = "FROM Invoice";
    private static final String FIND_BY_INVOICE_ID = "FROM Invoice where invoice_id = :invoiceId";

    public InvoiceDao() {
        super(Invoice.class);
    }

    public List<Invoice> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Invoice.class).getResultList();
    }

    public Invoice findByInvoiceId(String invoiceId) {
        try{
            return entityManager.createNamedQuery(FIND_BY_INVOICE_ID, Invoice.class)
                    .setParameter("invoiceId", invoiceId)
                    .getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }
}
