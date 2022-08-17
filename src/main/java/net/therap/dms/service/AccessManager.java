package net.therap.dms.service;

import net.therap.dms.entity.Invoice;
import net.therap.dms.entity.Prescription;
import net.therap.dms.entity.User;
import net.therap.dms.exception.InsufficientAccessException;
import net.therap.dms.helper.DoctorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static net.therap.dms.util.SessionUtil.getUser;
import static net.therap.dms.util.SessionUtil.isLoggedInUser;

/**
 * @author raian.rahman
 * @since 8/15/22
 */
@Component
public class AccessManager {

    @Autowired
    private DoctorHelper doctorHelper;

    public void checkInvoiceListAccess(long patientId, HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!(sessionUser.hasReceptionistRole()
                || (sessionUser.hasPatientRole() && sessionUser.getId() == patientId))) {

            throw new InsufficientAccessException();
        }
    }

    public void checkInvoiceDetailsAccess(Invoice invoice, HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!(sessionUser.hasReceptionistRole() ||
                (sessionUser.hasPatientRole() && invoice.getPatient().getId() == sessionUser.getId()))) {

            throw new InsufficientAccessException();
        }
    }

    public void checkInvoiceWriteAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!sessionUser.hasReceptionistRole()) {
            throw new InsufficientAccessException();
        }
    }

    public void checkUserDeleteAccess(User user, HttpServletRequest request) {
        if(isLoggedInUser(user, request)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkUserAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!sessionUser.hasAdminRole()) {
            throw new InsufficientAccessException();
        }
    }

    public void checkPatientListAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!sessionUser.hasDoctorRole()) {
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

        if (!sessionUser.hasPatientRole()) {
            throw new InsufficientAccessException();
        }
    }

    public void checkPrescriptionViewAccess(Prescription prescription, HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!sessionUser.hasDoctorRole()
                && !sessionUser.hasPatientRole()) {

            throw new InsufficientAccessException();
        }

        long patientId = prescription.getPatient().getId();

        if ((sessionUser.hasPatientRole() && patientId != sessionUser.getId())
                || (sessionUser.hasDoctorRole() && !doctorHelper.hasPatient(patientId, request))) {

            throw new InsufficientAccessException();
        }
    }
}
