package net.therap.service;

import net.therap.command.UserCmd;
import net.therap.dao.UserDao;
import net.therap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Service
public class AuthenticationService {

    @Autowired
    private UserDao userDao;

    public boolean authenticateByPassword(UserCmd userCmd) {
        User user = userDao.findByUserName(userCmd.getUserName());

        if (isNull(user)) {
            return false;
        }

        return user.getPassword().equals(userCmd.getPassword());
    }

    public boolean authenticateByRole(UserCmd userCmd, User user) {
        if (isNull(userCmd)) {
            return false;
        }

        return user.getRoles().contains(userCmd.getRole());
    }
}
