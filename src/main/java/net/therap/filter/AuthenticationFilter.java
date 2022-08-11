package net.therap.filter;

import net.therap.entity.Role;
import net.therap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static net.therap.constant.URL.*;
import static net.therap.entity.RoleEnum.*;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
//@Component
public class AuthenticationFilter implements Filter {

    private static final String LOGIN_REDIRECT_PATH = "/login";
    private static final String INVALID_ACCESS_REDIRECT_PATH = "/invalidPage";

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

        if (!sessionLoggedInFilter(httpServletRequest)) {
            httpServletResponse.sendRedirect(LOGIN_REDIRECT_PATH);
            return;
        }

        Role role = (Role) httpServletRequest.getSession().getAttribute("role");

        if (!invoiceAccessFilter(httpServletRequest, role)
                || !patientAccessFilter(httpServletRequest, role)
                || !userAccessFilter(httpServletRequest, role)
                || !prescriptionAccessFilter(httpServletRequest, role)) {
            httpServletResponse.sendRedirect(INVALID_ACCESS_REDIRECT_PATH);

            return;
        }

        chain.doFilter(request, response);
    }

    private boolean sessionLoggedInFilter(HttpServletRequest request) {
        return (!isNull(request.getSession().getAttribute("user"))
                && !isNull(request.getSession().getAttribute("role")))
                || request.getRequestURI().contains(LOGIN)
                || request.getRequestURI().contains(HOME)
                || request.getRequestURI().contains(LOGIN_ROLE)
                || request.getRequestURI().contains(LOGOUT)
                || request.getRequestURI().contains(ASSETS)
                || request.getRequestURI().contains(FAV_ICON);
    }

    private boolean invoiceAccessFilter(HttpServletRequest request, Role role) {

        if ((request.getRequestURI().contains(INVOICE_VIEW)
                || request.getRequestURI().contains(INVOICE_LIST))
                && !receptionistRole.equals(role)
                && !patientRole.equals(role)) {
            return false;
        }

        return (!request.getRequestURI().equals(INVOICE_SAVE)
                && !request.getRequestURI().contains(INVOICE_DOCTOR)
                && !request.getRequestURI().contains(INVOICE_FACILITY)
                && !request.getRequestURI().contains(INVOICE_MEDICINE))
                || receptionistRole.equals(role);
    }

    private boolean userAccessFilter(HttpServletRequest request, Role role) {
        return (!request.getRequestURI().contains(USER_LIST)
                && !request.getRequestURI().contains(USER_SAVE)
                && !request.getRequestURI().contains(USER_DELETE)
                && !request.getRequestURI().contains(USER_VIEW))
                || adminRole.equals(role);
    }

    private boolean patientAccessFilter(HttpServletRequest request, Role role) {
        if (request.getRequestURI().contains(PATIENT_LIST) && !doctorRole.equals(role)) {
            return false;
        }

        return !request.getRequestURI().contains(PATIENT_HISTORY)
                || doctorRole.equals(role)
                || patientRole.equals(role);
    }

    private boolean prescriptionAccessFilter(HttpServletRequest request, Role role) {
        if ((request.getRequestURI().contains(PRESCRIPTION_VIEW)
                || request.getRequestURI().contains(PRESCRIPTION_LIST))
                && !doctorRole.equals(role)
                && !patientRole.equals(role)) {
            return false;
        }

        return !request.getRequestURI().contains(PRESCRIPTION_SAVE)
                || doctorRole.equals(role);
    }
}
