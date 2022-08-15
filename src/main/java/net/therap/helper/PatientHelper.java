package net.therap.helper;

import net.therap.entity.Doctor;
import net.therap.entity.Patient;
import net.therap.entity.Prescription;
import net.therap.service.DoctorService;
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
    private DoctorService doctorService;

    public List<Prescription> getPrescriptions(Patient patient, Doctor doctor, boolean specific) {
        List<Prescription> selectedPrescriptions = new ArrayList<>();
        List<Prescription> allPrescriptions = patient.getPrescriptions();

        for (Prescription prescription : allPrescriptions) {
            if (specific && prescription.getDoctor().getId() == doctor.getId()) {
                selectedPrescriptions.add(prescription);
            } else if (!specific && prescription.getDoctor().getId() != doctor.getId()){
                selectedPrescriptions.add(prescription);
            }
        }

        Collections.sort(selectedPrescriptions);

        return selectedPrescriptions;
    }

    public Set<Patient> getPatientsForDoctor(Doctor doctor) {
        List<Prescription> prescriptions = doctorService.findById(doctor.getId()).getPrescriptions();
        Set<Patient> patients = new HashSet<>();

        for(Prescription prescription : prescriptions) {
            patients.add(prescription.getPatient());
        }

        return patients;
    }
}
