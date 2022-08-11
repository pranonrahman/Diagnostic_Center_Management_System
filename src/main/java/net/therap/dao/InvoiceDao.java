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

    public InvoiceDao() {
        super(Invoice.class);
    }

    public List<Invoice> findAll() {
        return em.createNamedQuery("Invoice.findAll", Invoice.class).getResultList();
    }

    public List<Invoice> findByPatient(Patient patient) {
        return em.createNamedQuery("Invoice.findByPatientId", Invoice.class)
                .setParameter("patientId", patient.getId())
                .getResultList();
    }
}
