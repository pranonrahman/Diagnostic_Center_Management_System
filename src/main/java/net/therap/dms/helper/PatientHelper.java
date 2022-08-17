package net.therap.dms.helper;

import net.therap.dms.entity.Doctor;
import net.therap.dms.entity.Patient;
import net.therap.dms.entity.Prescription;
import net.therap.dms.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author amimul.ehsan
 * @since 15/08/2022
 */
@Component
public class PatientHelper {

    @Autowired
    private PrescriptionService prescriptionService;

    public List<Prescription> getPrescriptions(Patient patient, Doctor doctor, boolean specific) {
        List<Prescription> selectedPrescriptions = new ArrayList<>();
        List<Prescription> allPrescriptions = patient.getPrescriptions();

        for (Prescription prescription : allPrescriptions) {
            if (specific && prescription.getDoctor().getId() == doctor.getId()) {
                selectedPrescriptions.add(prescription);
            } else if (!specific && prescription.getDoctor().getId() != doctor.getId()) {
                selectedPrescriptions.add(prescription);
            }
        }

        Collections.sort(selectedPrescriptions);

        return selectedPrescriptions;
    }

    public Set<Patient> getPatientsForDoctor(Doctor doctor) {
        List<Prescription> prescriptions = prescriptionService.findByDoctor(doctor.getId());
        Set<Patient> patients = new HashSet<>();

        for (Prescription prescription : prescriptions) {
            patients.add(prescription.getPatient());
        }

        return patients;
    }
}
