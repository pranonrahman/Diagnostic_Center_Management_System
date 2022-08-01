package net.therap.model;

import java.util.Date;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Prescription extends BaseEntity {

    private String symptoms;
    private String diagnosis;
    private String comment;
    private Date dateOfVisit;
    private Patient patient;
    private Doctor doctor;
    private List<Service> serviceList;
    private List<Medicine> medicineList;

    public Prescription() {
    }

    public Prescription(Long id, String symptoms, String diagnosis, String comment, Date dateOfVisit, Patient patient,
                        Doctor doctor, List<Service> serviceList, List<Medicine> medicineList) {

        super(id);
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.comment = comment;
        this.dateOfVisit = dateOfVisit;
        this.patient = patient;
        this.doctor = doctor;
        this.serviceList = serviceList;
        this.medicineList = medicineList;
    }
}
