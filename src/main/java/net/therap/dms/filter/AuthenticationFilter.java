package net.therap.dms.filter;

import net.therap.dms.constant.URL;
import net.therap.dms.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.therap.dms.util.AccessManager.*;
import static net.therap.dms.util.SessionUtil.getUser;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
@Component
public class AuthenticationFilter implements Filter, URL {

    private static final String LOGIN_REDIRECT_PATH = "/login";
    private static final String HOME_REDIRECT_PATH = "/";
    private static final String INVALID_ACCESS_REDIRECT_PATH = "/invalidPage";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);

        if (isLoggedIn(httpServletRequest)) {
            User user = getUser(httpServletRequest);

            if (httpServletRequest.getRequestURI().contains(LOGIN)) {
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

        if (!isLoggedIn(httpServletRequest)
                && !httpServletRequest.getRequestURI().equals(LOGIN)
                && !httpServletRequest.getRequestURI().equals(LOGOUT)
                && !httpServletRequest.getRequestURI().contains(ASSETS)
                && !httpServletRequest.getRequestURI().contains(FAV_ICON)) {

            httpServletResponse.sendRedirect(LOGIN_REDIRECT_PATH);

            return;
        }


        chain.doFilter(request, response);
    }
}
