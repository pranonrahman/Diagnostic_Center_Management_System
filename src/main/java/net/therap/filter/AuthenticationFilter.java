package net.therap.filter;

import net.therap.model.Role;
import net.therap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static net.therap.model.RoleEnum.*;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
@Component
public class AuthenticationFilter implements Filter {

    private static final String LOGIN_REDIRECT_PATH = "/login";
    private static final String INVALID_ACCESS_REDIRECT_PATH = "/invalidPage";

    @Autowired
    private RoleService roleService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Role adminRole = roleService.findByRole(ADMIN);
        Role doctorRole = roleService.findByRole(DOCTOR);
        Role patientRole = roleService.findByRole(PATIENT);
        Role receptionistRole = roleService.findByRole(RECEPTIONIST);

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);

        if ((isNull(httpServletRequest.getSession().getAttribute("user")) || isNull(httpServletRequest.getSession().getAttribute("role"))) && !httpServletRequest.getRequestURI().contains("login") && !httpServletRequest.getRequestURI().contains("login/role") && !httpServletRequest.getRequestURI().contains("logout") && !httpServletRequest.getRequestURI().contains("assets/")) {

            httpServletResponse.sendRedirect(LOGIN_REDIRECT_PATH);

            return;
        }

        Role role = (Role) httpServletRequest.getSession().getAttribute("role");

        if (httpServletRequest.getRequestURI().contains("invoice/view") && !receptionistRole.equals(role) && !patientRole.equals(role)) {

            httpServletResponse.sendRedirect(INVALID_ACCESS_REDIRECT_PATH);

            return;
        }

        if ((httpServletRequest.getRequestURI().contains("invoice/save")
                || httpServletRequest.getRequestURI().contains("invoice/list"))
                && !receptionistRole.equals(role)) {

            httpServletResponse.sendRedirect(INVALID_ACCESS_REDIRECT_PATH);

            return;
        }

        if (httpServletRequest.getRequestURI().contains("prescription/view")
                && !doctorRole.equals(role)
                && !patientRole.equals(role)) {

            httpServletResponse.sendRedirect(INVALID_ACCESS_REDIRECT_PATH);

            return;
        }

        if (httpServletRequest.getRequestURI().contains("prescription/save") && !doctorRole.equals(role)) {

            httpServletResponse.sendRedirect(INVALID_ACCESS_REDIRECT_PATH);

            return;
        }

        if (httpServletRequest.getRequestURI().contains("patient/history")
                && !doctorRole.equals(role)
                && !patientRole.equals(role)) {

            httpServletResponse.sendRedirect(INVALID_ACCESS_REDIRECT_PATH);

            return;
        }

        if (httpServletRequest.getRequestURI().contains("doctor/patients")
                && !doctorRole.equals(role)) {

            httpServletResponse.sendRedirect(INVALID_ACCESS_REDIRECT_PATH);

            return;
        }

        if ((httpServletRequest.getRequestURI().contains("person/list")
                || httpServletRequest.getRequestURI().contains("person/save")
                || httpServletRequest.getRequestURI().contains("person/delete")
                || httpServletRequest.getRequestURI().contains("person/view"))
                && !adminRole.equals(role)) {

            httpServletResponse.sendRedirect(INVALID_ACCESS_REDIRECT_PATH);
            return;
        }

        chain.doFilter(request, response);
    }
}
