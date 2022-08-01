package net.therap.service;

import net.therap.dao.InvoiceDao;
import net.therap.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    public List<Invoice> findAll() {
        return invoiceDao.findAll();
    }

    public Invoice findById(Long id) {
        return invoiceDao.findById(id);
    }

    @Transactional
    public Invoice saveOrUpdate(Invoice invoice) {
        if(invoice.isNew()) {
            invoice = invoiceDao.save(invoice);
        } else {
            invoice = invoiceDao.update(invoice);
        }

        return invoice;
    }

    @Transactional
    public void delete(Invoice invoice) {
        invoiceDao.delete(invoice);
    }
}
