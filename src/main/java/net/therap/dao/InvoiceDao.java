package net.therap.dao;

import net.therap.model.Invoice;
import net.therap.model.Patient;
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
    private static final String FIND_ALL_BY_PATIENT = "FROM Invoice where patient.id = :patientId";

    public InvoiceDao() {
        super(Invoice.class);
    }

    public List<Invoice> findAll() {
        return entityManager.createQuery(FIND_ALL_QUERY, Invoice.class).getResultList();
    }

    public Invoice findByInvoiceId(String invoiceId) {
        try{
            return entityManager.createQuery(FIND_BY_INVOICE_ID, Invoice.class)
                    .setParameter("invoiceId", invoiceId)
                    .getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    public List<Invoice> findAllByPatient(Patient patient) {
        try{
            return entityManager.createQuery(FIND_ALL_BY_PATIENT, Invoice.class)
                    .setParameter("patientId", patient.getId())
                    .getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }
}
