package net.therap.dms.service;

import net.therap.dms.command.FacilityItemCmd;
import net.therap.dms.command.InvoiceCmd;
import net.therap.dms.command.MedicineItemCmd;
import net.therap.dms.dao.InvoiceDao;
import net.therap.dms.entity.*;
import net.therap.dms.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

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

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicineService medicineService;

    public Invoice findById(long id) {
        Invoice invoice = invoiceDao.findById(id);

        if (isNull(invoice)) {
            throw new RecordNotFoundException();
        }

        return invoice;
    }

    public List<Invoice> findByPatient(Patient patient) {
        return invoiceDao.findByPatient(patient);
    }

    public List<Invoice> findAll() {
        return invoiceDao.findAll();
    }

    @Transactional
    public Invoice saveOrUpdate(Invoice invoice) {
        return invoiceDao.saveOrUpdate(invoice);
    }

    public Invoice getInvoiceFromCmd(InvoiceCmd invoiceCmd) {
        Invoice invoice = new Invoice();
        invoice.setPatient(invoiceCmd.getPatient());
        invoice.setReceptionist(invoiceCmd.getReceptionist());

        invoice.getParticulars().addAll(getDoctorVisitParticulars(invoiceCmd.getDoctors()));
        invoice.getParticulars().addAll(getMedicineParticulars(invoiceCmd.getMedicines()));
        invoice.getParticulars().addAll(getFacilityParticulars(invoiceCmd.getFacilities()));

        double totalCost = 0;
        for (Particular particular : invoice.getParticulars()) {
            totalCost += particular.getUnitPrice() * particular.getUnits();
        }

        invoice.setTotalCost(totalCost);

        return invoice;
    }

    private List<Particular> getDoctorVisitParticulars(List<Doctor> doctorVisits) {
        return doctorVisits
                .stream()
                .map(doctor -> new Particular("Visiting fee of " + doctor.getUser().getName(),
                        doctor.getFee(),
                        1))
                .collect(Collectors.toList());
    }

    private List<Particular> getMedicineParticulars(List<MedicineItemCmd> medicines) {
        return medicines
                .stream()
                .map(medicineItem -> new Particular(medicineItem.getMedicine().getName(),
                        medicineItem.getMedicine().getUnitPrice(),
                        medicineItem.getQuantity()))
                .collect(Collectors.toList());
    }

    private List<Particular> getFacilityParticulars(List<FacilityItemCmd> facilityItems) {
        return facilityItems
                .stream()
                .map(facilityItem -> new Particular(facilityItem.getFacility().getName(),
                        facilityItem.getFacility().getPrice(),
                        facilityItem.getQuantity()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Invoice invoice) {
        invoiceDao.delete(invoice);
    }

    public void createEmptyPrescriptions(InvoiceCmd invoiceCmd) {
        for (Doctor doctor : invoiceCmd.getDoctors()) {
            Prescription prescription = new Prescription();
            prescription.setPatient(invoiceCmd.getPatient());
            prescription.setDoctor(doctor);

            prescriptionService.saveOrUpdate(prescription);
        }
    }

    public void updateMedicineQuantity(InvoiceCmd invoiceCmd) {
        for (MedicineItemCmd medicineItem : invoiceCmd.getMedicines()) {
            Medicine updatedMedicine = medicineItem.getMedicine();
            updatedMedicine.setAvailableUnits(updatedMedicine.getAvailableUnits() - medicineItem.getQuantity());

            medicineService.saveOrUpdate(updatedMedicine);
        }
    }
}
