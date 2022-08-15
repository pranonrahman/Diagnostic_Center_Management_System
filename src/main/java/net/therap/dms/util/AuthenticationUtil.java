package net.therap.dms.util;

import net.therap.dms.command.UserCmd;
import net.therap.dms.dao.UserDao;
import net.therap.dms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Component
public class AuthenticationUtil {

    @Autowired
    private UserDao userDao;

    public boolean isValidCredential(UserCmd userCmd) {
        User user = userDao.findByUserName(userCmd.getUserName());

        return nonNull(user) && user.getPassword().equals(userCmd.getPassword());
    }
}
