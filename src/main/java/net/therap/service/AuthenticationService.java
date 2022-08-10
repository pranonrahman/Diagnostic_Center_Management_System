package net.therap.service;

import net.therap.dao.UserDao;
import net.therap.model.User;
import net.therap.viewModel.UserViewModel;
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

    public boolean authenticateByPassword(UserViewModel userViewModel) {
        User user = userDao.findByUserName(userViewModel.getUserName());

        if (isNull(user)) {
            return false;
        }

        return user.getPassword().equals(userViewModel.getPassword());
    }

    public boolean authenticateByRole(UserViewModel userViewModel, User user) {

        if (isNull(userViewModel)) {
            return false;
        }

        return user.getRoles().contains(userViewModel.getRole());
    }
}
