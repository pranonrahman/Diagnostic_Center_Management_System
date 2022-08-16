package net.therap.dms.service;

import net.therap.dms.entity.Doctor;
import net.therap.dms.entity.Prescription;
import net.therap.dms.entity.User;
import net.therap.dms.exception.InsufficientAccessException;
import net.therap.dms.helper.DoctorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static net.therap.dms.entity.RoleEnum.*;
import static net.therap.dms.util.RoleUtil.userContains;
import static net.therap.dms.util.SessionUtil.getUser;

/**
 * @author raian.rahman
 * @since 8/15/22
 */
@Component
public class AccessManager {

    @Autowired
    private DoctorHelper doctorHelper;


    public void checkInvoiceAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (request.getMethod().equals("POST") && !userContains(sessionUser, RECEPTIONIST)) {
            throw new InsufficientAccessException();
        }

        if (!userContains(sessionUser, RECEPTIONIST) && !userContains(sessionUser, PATIENT)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkUserAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!userContains(sessionUser, ADMIN)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkPatientListAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!userContains(sessionUser, DOCTOR)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkPatientHistoryAccess(long patientId, HttpServletRequest request) {
        checkPatientListAccess(request);

        if (!doctorHelper.hasPatient(patientId, request)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkPrescriptionListAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!userContains(sessionUser, PATIENT)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkPrescriptionViewAccess(Prescription prescription, HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!userContains(sessionUser, DOCTOR)
                && !userContains(sessionUser, PATIENT)) {

            throw new InsufficientAccessException();
        }

        long patientId = prescription.getPatient().getId();

        if ((userContains(sessionUser, PATIENT) && patientId != sessionUser.getId())
                || (userContains(sessionUser, DOCTOR) && !doctorHelper.hasPatient(patientId, request))) {

            throw new InsufficientAccessException();
        }
    }
}
