package net.therap.model;

import java.util.Date;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Patient extends Person {

    private List<Prescription> prescriptionList;
    private List<Invoice> invoiceList;

    public Patient() {
    }

    public Patient(Long id, String name, String phone, String email, Gender gender,
                   Date dateOfBirth, List<Role> roleList, String userName, String password,
                   List<Prescription> prescriptionList, List<Invoice> invoiceList) {

        super(id, name, phone, email, gender, dateOfBirth, roleList, userName, password);
        this.prescriptionList = prescriptionList;
        this.invoiceList = invoiceList;
    }

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }
}
