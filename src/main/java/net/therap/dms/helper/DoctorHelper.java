package net.therap.dms.helper;

import net.therap.dms.entity.Doctor;
import net.therap.dms.entity.Prescription;
import net.therap.dms.entity.User;
import net.therap.dms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static net.therap.dms.entity.RoleEnum.DOCTOR;
import static net.therap.dms.util.RoleUtil.hasRole;
import static net.therap.dms.util.SessionUtil.getUser;

/**
 * @author amimul.ehsan
 * @since 16/08/2022
 */
@Component
public class DoctorHelper {

    @Autowired
    private DoctorService doctorService;

    public boolean hasPatient(long patientId, HttpServletRequest request) {
        Doctor doctor = doctorService.findById(getUser(request).getId());

        for(Prescription prescription : doctor.getPrescriptions()) {
            if(prescription.getPatient().getId() == patientId) {

                return true;
            }
        }

        return false;
    }

    public boolean hasPrescription(Prescription prescription, HttpServletRequest request) {
        User user = getUser(request);

        return hasRole(user, DOCTOR) &&
                (user.getDoctor().getId() == prescription.getDoctor().getId());
    }
}
