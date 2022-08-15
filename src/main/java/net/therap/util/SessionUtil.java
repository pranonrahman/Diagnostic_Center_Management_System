package net.therap.util;

import net.therap.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author khandaker.maruf
 * @since 8/15/22
 */
public class SessionUtil {

    public static User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }
}
