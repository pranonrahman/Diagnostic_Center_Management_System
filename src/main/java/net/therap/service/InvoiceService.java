package net.therap.service;

import net.therap.dao.InvoiceDao;
import net.therap.model.Invoice;
import net.therap.model.Particular;
import net.therap.model.Receptionist;
import net.therap.viewModel.InvoiceViewModel;
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

    public Invoice findById(long id) {
        return invoiceDao.findById(id);
    }

    public Invoice findByInvoiceId(String invoiceId) {
        return invoiceDao.findByInvoiceId(invoiceId);
    }

    @Transactional
    public Invoice saveOrUpdate(Invoice invoice) {
        return invoiceDao.saveOrUpdate(invoice);
    }

    public Invoice getInvoiceFromViewModel(InvoiceViewModel invoiceViewModel) {
        Invoice invoice = new Invoice();
        invoice.setPatient(invoiceViewModel.getPatient());
        double totalCost = 0;

        invoiceViewModel.getDoctors().forEach(doctorItem-> {
            Particular particular = new Particular("Visiting fee of " + doctorItem.getPerson().getName(),
                    doctorItem.getFee(),
                    1);

            invoice.getParticulars().add(particularService.saveOrUpdate(particular));
        });

        invoiceViewModel.getFacilities().forEach(facilityItem -> {
            Particular particular = new Particular(facilityItem.getFacility().getName(),
                    facilityItem.getFacility().getPrice(),
                    facilityItem.getQuantity());

            invoice.getParticulars().add(particularService.saveOrUpdate(particular));
        });

        invoiceViewModel.getMedicines().forEach(medicineItem -> {
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
