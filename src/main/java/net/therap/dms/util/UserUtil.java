package net.therap.dms.util;

import net.therap.dms.entity.User;

import javax.servlet.http.HttpServletRequest;

import static net.therap.dms.util.SessionUtil.getUser;

/**
 * @author raian.rahman
 * @since 8/15/22
 */
public class UserUtil {

    public static boolean isSessionUser(HttpServletRequest request, User user) {
        return getUser(request).getUserName().equals(user.getUserName());
    }
}
