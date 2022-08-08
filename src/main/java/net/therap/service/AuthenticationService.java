package net.therap.service;

import net.therap.dao.PersonDao;
import net.therap.model.Person;
import net.therap.viewModel.PersonViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Service
public class AuthenticationService {

    @Autowired
    private PersonDao personDao;

    public boolean authenticateByPassword(PersonViewModel user) {
        Person person = personDao.findByUserName(user.getUserName());

        if (isNull(person)) {
            return false;
        }

        return Objects.equals(person.getPassword(), user.getPassword());
    }

    public boolean authenticateByRole(PersonViewModel user, Person person) {

        if(isNull(person)) {
            return false;
        }

        return person.getRoles().contains(user.getRole());
    }
}
