package net.therap.dms.helper;

import net.therap.dms.constant.URL;
import net.therap.dms.entity.Role;
import net.therap.dms.entity.User;
import net.therap.dms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.nonNull;
import static net.therap.dms.entity.RoleEnum.*;
import static net.therap.dms.entity.RoleEnum.RECEPTIONIST;

/**
 * @author raian.rahman
 * @since 8/15/22
 */
@Component
public class AccessControlHelper implements URL {

    private static final String USER = "user";

    private final Role adminRole;
    private final Role doctorRole;
    private final Role patientRole;
    private final Role receptionistRole;

    @Autowired
    private RoleService roleService;

    public AccessControlHelper() {
        adminRole = roleService.findByName(ADMIN);
        doctorRole = roleService.findByName(DOCTOR);
        patientRole = roleService.findByName(PATIENT);
        receptionistRole = roleService.findByName(RECEPTIONIST);
    }

    public boolean isLoggedIn(HttpServletRequest request) {
        return nonNull(request.getSession().getAttribute(USER));
    }

    public boolean hasInvoiceAccess(HttpServletRequest request, User user) {
        if ((request.getRequestURI().equals(INVOICE)
                || request.getRequestURI().contains(INVOICE_LIST))
                && !user.getRoles().contains(receptionistRole)
                && !user.getRoles().contains(patientRole)) {
            return false;
        }

        return (!request.getRequestURI().contains(INVOICE_DOCTOR)
                && !request.getRequestURI().contains(INVOICE_FACILITY)
                && !request.getRequestURI().contains(INVOICE_MEDICINE))
                || user.getRoles().contains(receptionistRole);
    }

    public boolean hasUserAccess(HttpServletRequest request, User user) {
        return (!request.getRequestURI().contains(USER_LIST)
                && !request.getRequestURI().contains(USER_VIEW)
                && !request.getRequestURI().contains(USER_DELETE))
                || user.getRoles().contains(adminRole);
    }

    public boolean hasPatientAccess(HttpServletRequest request, User user) {
        if (request.getRequestURI().contains(PATIENT_LIST) && !user.getRoles().contains(doctorRole)) {
            return false;
        }

        return !request.getRequestURI().contains(PATIENT_HISTORY)
                || user.getRoles().contains(doctorRole)
                || user.getRoles().contains(patientRole);
    }

    public boolean hasPrescriptionAccess(HttpServletRequest request, User user) {
        if ((request.getRequestURI().equals(PRESCRIPTION)
                || request.getRequestURI().contains(PRESCRIPTION_LIST))
                && !user.getRoles().contains(doctorRole)
                && !user.getRoles().contains(patientRole)) {
            return false;
        }

        return !request.getRequestURI().contains(PRESCRIPTION_SAVE)
                || user.getRoles().contains(doctorRole);
    }
}
