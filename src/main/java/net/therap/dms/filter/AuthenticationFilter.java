package net.therap.dms.filter;

import net.therap.dms.constant.URL;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
@Component
public class AuthenticationFilter implements Filter, URL {

    private static final String LOGIN_REDIRECT_PATH = "/login";
    private static final String HOME_REDIRECT_PATH = "/";

    private static final String USER = "user";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);

        if (nonNull(httpServletRequest.getSession().getAttribute(USER))) {

            if (httpServletRequest.getRequestURI().contains(LOGIN)) {
                httpServletResponse.sendRedirect(HOME_REDIRECT_PATH);
                return;
            }
        }

        if (isNull(httpServletRequest.getSession().getAttribute(USER))
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
