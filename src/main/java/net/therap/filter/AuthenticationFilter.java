package net.therap.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
public class AuthenticationFilter implements Filter {

    private static final String LOGIN_REDIRECT_PATH = "authentication/form";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);

        if (isNull(httpServletRequest.getSession().getAttribute("user"))
                && !httpServletRequest.getRequestURI().contains("login")) {

            httpServletResponse.sendRedirect(LOGIN_REDIRECT_PATH);

            return;
        }

        chain.doFilter(request, response);
    }
}
