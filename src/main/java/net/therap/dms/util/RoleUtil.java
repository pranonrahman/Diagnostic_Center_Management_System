package net.therap.dms.util;

import net.therap.dms.entity.RoleEnum;
import net.therap.dms.entity.User;

/**
 * @author khandaker.maruf
 * @since 8/11/22
 */
public class RoleUtil {

    public static boolean hasRole(User user, RoleEnum roleEnum) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(roleEnum));
    }
}
