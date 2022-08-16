package net.therap.dms.util;

import net.therap.dms.constant.URL;
import net.therap.dms.entity.User;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.nonNull;
import static net.therap.dms.entity.RoleEnum.*;
import static net.therap.dms.entity.RoleEnum.RECEPTIONIST;
import static net.therap.dms.util.RoleUtil.userContains;

/**
 * @author raian.rahman
 * @since 8/15/22
 */
public class AccessManager implements URL {

    private static final String USER = "user";

    public static boolean isLoggedIn(HttpServletRequest request) {
        return nonNull(request.getSession().getAttribute(USER));
    }

    public static boolean hasInvoiceAccess(HttpServletRequest request, User user) {
        if ((request.getRequestURI().equals(INVOICE)
                || request.getRequestURI().contains(INVOICE_LIST))
                && !userContains(user, RECEPTIONIST)
                && !userContains(user, PATIENT)) {
            return false;
        }

        return (!request.getRequestURI().contains(INVOICE_DOCTOR)
                && !request.getRequestURI().contains(INVOICE_FACILITY)
                && !request.getRequestURI().contains(INVOICE_MEDICINE))
                || userContains(user, RECEPTIONIST);
    }

    public static boolean hasUserAccess(HttpServletRequest request, User user) {
        return (!request.getRequestURI().contains(USER_LIST)
                && !request.getRequestURI().contains(USER_VIEW)
                && !request.getRequestURI().contains(USER_DELETE))
                || userContains(user, ADMIN);
    }

    public static boolean hasPatientAccess(HttpServletRequest request, User user) {
        if (request.getRequestURI().contains(PATIENT_LIST) && !userContains(user, DOCTOR)) {
            return false;
        }

        return !request.getRequestURI().contains(PATIENT_HISTORY)
                || userContains(user, DOCTOR)
                || userContains(user, PATIENT);
    }

    public static boolean hasPrescriptionAccess(HttpServletRequest request, User user) {
        if ((request.getRequestURI().equals(PRESCRIPTION)
                || request.getRequestURI().contains(PRESCRIPTION_LIST))
                && !userContains(user, DOCTOR)
                && !userContains(user, PATIENT)) {
            return false;
        }

        return !request.getRequestURI().contains(PRESCRIPTION_SAVE)
                || userContains(user, DOCTOR);
    }
}
