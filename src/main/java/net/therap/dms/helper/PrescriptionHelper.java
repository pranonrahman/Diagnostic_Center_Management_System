package net.therap.dms.helper;

import net.therap.dms.entity.Patient;
import net.therap.dms.entity.Prescription;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author amimul.ehsan
 * @since 15/08/2022
 */
@Component
public class PrescriptionHelper {

    public List<Prescription> getPrescriptionsForPatient(Patient patient){
        List<Prescription> prescriptions = patient.getPrescriptions();

        Collections.sort(prescriptions);
        
        return prescriptions;
    }
}
