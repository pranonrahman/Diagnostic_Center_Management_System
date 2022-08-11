package net.therap.service;

import net.therap.dao.InvoiceDao;
import net.therap.model.Invoice;
import net.therap.model.Particular;
import net.therap.model.Patient;
import net.therap.command.InvoiceCmd;
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

    @Autowired
    ReceptionistService receptionistService;

    @Autowired
    ParticularService particularService;

    public List<Invoice> findAll() {
        return invoiceDao.findAll();
    }

    public List<Invoice> findAllByPatient(Patient patient) {
        return invoiceDao.findAllByPatient(patient);
    }

    public Invoice findById(long id) {
        return invoiceDao.findById(id);
    }

    @Transactional
    public Invoice saveOrUpdate(Invoice invoice) {
        return invoiceDao.saveOrUpdate(invoice);
    }

    public Invoice getInvoiceFromViewModel(InvoiceCmd invoiceCmd) {
        Invoice invoice = new Invoice();
        invoice.setPatient(invoiceCmd.getPatient());
        double totalCost = 0;

        invoiceCmd.getDoctors().forEach(doctorItem-> {
            Particular particular = new Particular("Visiting fee of " + doctorItem.getUser().getName(),
                    doctorItem.getFee(),
                    1);

            invoice.getParticulars().add(particularService.saveOrUpdate(particular));
        });

        invoiceCmd.getFacilities().forEach(facilityItem -> {
            Particular particular = new Particular(facilityItem.getFacility().getName(),
                    facilityItem.getFacility().getPrice(),
                    facilityItem.getQuantity());

            invoice.getParticulars().add(particularService.saveOrUpdate(particular));
        });

        invoiceCmd.getMedicines().forEach(medicineItem -> {
            Particular particular = new Particular(medicineItem.getMedicine().getName(),
                    medicineItem.getMedicine().getUnitPrice(),
                    medicineItem.getQuantity());

            invoice.getParticulars().add(particularService.saveOrUpdate(particular));
        });

        for (Particular particular : invoice.getParticulars()) {
            totalCost += particular.getUnitPrice() * particular.getUnits();
        }

        invoice.setTotalCost(totalCost);

        return invoice;
    }

    @Transactional
    public void delete(Invoice invoice) {
        invoiceDao.delete(invoice);
    }
}
