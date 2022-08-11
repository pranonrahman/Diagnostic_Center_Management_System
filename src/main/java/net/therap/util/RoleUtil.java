package net.therap.util;

import net.therap.entity.RoleEnum;
import net.therap.entity.User;

/**
 * @author khandaker.maruf
 * @since 8/11/22
 */
public class RoleUtil {

    public static boolean userContains(User user, RoleEnum roleEnum) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(roleEnum));
    }

    public static boolean userNotContains(User user, RoleEnum roleEnum) {
        return user.getRoles().stream().noneMatch(role -> role.getName().equals(roleEnum));
    }
}
