package net.therap.filter;

import net.therap.constant.URL;
import net.therap.entity.Role;
import net.therap.entity.User;
import net.therap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static net.therap.entity.RoleEnum.*;
import static net.therap.util.SessionUtil.getUser;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
@Component
public class AuthenticationFilter implements Filter, URL {

    private static final String LOGIN_REDIRECT_PATH = "/login";
    private static final String HOME_REDIRECT_PATH = "/";
    private static final String INVALID_ACCESS_REDIRECT_PATH = "/invalidPage";

    private static final String USER = "user";

    private Role adminRole;
    private Role doctorRole;
    private Role patientRole;
    private Role receptionistRole;

    @Autowired
    private RoleService roleService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        adminRole = roleService.findByName(ADMIN);
        doctorRole = roleService.findByName(DOCTOR);
        patientRole = roleService.findByName(PATIENT);
        receptionistRole = roleService.findByName(RECEPTIONIST);

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);

        if(isLoggedIn(httpServletRequest)) {
            User user = getUser(httpServletRequest);

            if(httpServletRequest.getRequestURI().contains(LOGIN)) {
                httpServletResponse.sendRedirect(HOME_REDIRECT_PATH);
                return;
            }

            if (!hasInvoiceAccess(httpServletRequest, user)
                    || !hasPatientAccess(httpServletRequest, user)
                    || !hasUserAccess(httpServletRequest, user)
                    || !hasPrescriptionAccess(httpServletRequest, user)) {

                httpServletResponse.sendRedirect(INVALID_ACCESS_REDIRECT_PATH);

                return;
            }
        }

        if(!isLoggedIn(httpServletRequest)
            && !httpServletRequest.getRequestURI().equals(LOGIN)
            && !httpServletRequest.getRequestURI().equals(LOGOUT)
            && !httpServletRequest.getRequestURI().contains(ASSETS)
            && !httpServletRequest.getRequestURI().contains(FAV_ICON)) {

            httpServletResponse.sendRedirect(LOGIN_REDIRECT_PATH);
            return;
        }


        chain.doFilter(request, response);
    }

    private boolean isLoggedIn(HttpServletRequest request) {
        return nonNull(request.getSession().getAttribute(USER));
    }

    private boolean hasInvoiceAccess(HttpServletRequest request, User user) {
        if ((request.getRequestURI().equals(INVOICE_VIEW)
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

    private boolean hasUserAccess(HttpServletRequest request, User user) {
        return (!request.getRequestURI().contains(USER_LIST)
                && !request.getRequestURI().contains(USER_VIEW)
                && !request.getRequestURI().contains(USER_DELETE))
                || user.getRoles().contains(adminRole);
    }

    private boolean hasPatientAccess(HttpServletRequest request, User user) {
        if (request.getRequestURI().contains(PATIENT_LIST) && !user.getRoles().contains(doctorRole)) {
            return false;
        }

        return !request.getRequestURI().contains(PATIENT_HISTORY)
                || user.getRoles().contains(doctorRole)
                || user.getRoles().contains(patientRole);
    }

    private boolean hasPrescriptionAccess(HttpServletRequest request, User user) {
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
