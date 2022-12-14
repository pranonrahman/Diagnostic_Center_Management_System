package net.therap.dms.helper;

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
public class AuthenticationHelper {

    @Autowired
    private UserDao userDao;

    public boolean isValidCredential(User user) {
        User savedUser = userDao.findByUserName(user.getUserName());

        return nonNull(savedUser) && savedUser.getPassword().equals(user.getPassword());
    }
}
