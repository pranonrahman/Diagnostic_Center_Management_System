package net.therap.dms.service;

import net.therap.dms.entity.User;
import net.therap.dms.exception.InsufficientAccessException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static net.therap.dms.constant.URL.PRESCRIPTION_SAVE;
import static net.therap.dms.entity.RoleEnum.*;
import static net.therap.dms.util.RoleUtil.userContains;
import static net.therap.dms.util.SessionUtil.getUser;

/**
 * @author raian.rahman
 * @since 8/15/22
 */
@Component
public class AccessManager {


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

        if(!userContains(sessionUser, ADMIN)) {
            throw new InsufficientAccessException();
        }
    }

    public void checkPatientAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!userContains(sessionUser, DOCTOR)) {
            throw new InsufficientAccessException();
        }
    }

    public void hasPrescriptionAccess(HttpServletRequest request) {
        User sessionUser = getUser(request);

        if (!userContains(sessionUser, DOCTOR)
                && !userContains(sessionUser, PATIENT)) {

            throw new InsufficientAccessException();
        }

        if(request.getRequestURI().contains(PRESCRIPTION_SAVE)
                && !userContains(sessionUser, DOCTOR)) {

            throw new InsufficientAccessException();
        }
    }
}
