package net.therap.dms.service;

import net.therap.dms.entity.Invoice;
import net.therap.dms.entity.Prescription;
import net.therap.dms.entity.User;
import net.therap.dms.exception.InsufficientAccessException;
import net.therap.dms.helper.DoctorHelper;
import net.therap.dms.util.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static net.therap.dms.entity.RoleEnum.*;
import static net.therap.dms.util.RoleUtil.hasRole;
import static net.therap.dms.util.SessionUtil.getUser;

/**
 * @author raian.rahman
 * @since 8/15/22
 */
@Component
public class AccessManager {

    @Autowired
    private DoctorHelper doctorHelper;

    public void checkInvoiceListAccess(long patientId, HttpServletRequest request) {
        User user = getUser(request);

        if (!(RoleUtil.hasRole(user, RECEPTIONIST)
                || (RoleUtil.hasRole(user, PATIENT) && user.getId() == patientId))) {

            throw new InsufficientAccessException();
        }
    }

    public void checkInvoiceDetailsAccess(Invoice invoice, HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!(hasRole(sessionUser, RECEPTIONIST) ||
                (hasRole(sessionUser, PATIENT) && invoice.getPatient().getId() == sessionUser.getId()))) {

            throw new InsufficientAccessException();
        }
    }

    public void checkInvoiceWriteAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!hasRole(sessionUser, RECEPTIONIST)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkUserAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!hasRole(sessionUser, ADMIN)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkPatientListAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!hasRole(sessionUser, DOCTOR)) {
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

        if (!hasRole(sessionUser, PATIENT)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkPrescriptionViewAccess(Prescription prescription, HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!hasRole(sessionUser, DOCTOR)
                && !hasRole(sessionUser, PATIENT)) {

            throw new InsufficientAccessException();
        }

        long patientId = prescription.getPatient().getId();

        if ((hasRole(sessionUser, PATIENT) && patientId != sessionUser.getId())
                || (hasRole(sessionUser, DOCTOR) && !doctorHelper.hasPatient(patientId, request))) {

            throw new InsufficientAccessException();
        }
    }
}
