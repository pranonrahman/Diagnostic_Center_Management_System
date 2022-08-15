package net.therap.util;

import net.therap.command.UserCmd;
import net.therap.dao.UserDao;
import net.therap.entity.User;
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
