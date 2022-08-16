package net.therap.dms.util;

import net.therap.dms.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author khandaker.maruf
 * @since 8/15/22
 */
public class SessionUtil {

    public static User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    public static boolean isLoggedInUser(User user, HttpServletRequest request) {
        return getUser(request).getUserName().equals(user.getUserName());
    }
}
