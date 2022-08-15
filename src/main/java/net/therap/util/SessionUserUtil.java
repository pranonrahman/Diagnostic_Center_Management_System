package net.therap.util;

import net.therap.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author raian.rahman
 * @since 8/15/22
 */
public class SessionUserUtil {

    private static final String USER = "user";

    public static User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(USER);
    }
}
