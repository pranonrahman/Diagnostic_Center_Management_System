package net.therap.service;

import net.therap.dao.PersonDao;
import net.therap.model.Doctor;
import net.therap.model.Person;
import net.therap.viewModel.RoleUpdateViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private DoctorService doctorService;

    public List<Person> findAll() {
        return personDao.findAll();
    }

    public Person findById(long id) {
        return personDao.findById(id);
    }

    @Transactional
    public Person saveOrUpdate(Person person) {
        return personDao.saveOrUpdate(person);
    }

    @Transactional
    public void delete(Person person) {
        personDao.delete(person);
    }

    @Transactional
    public Person updateRole(Person person, RoleUpdateViewModel roleUpdateViewModel) {

        if(isNull(person.getDoctor()) && roleUpdateViewModel.getDoctor()) {
            Doctor doctor = new Doctor(roleUpdateViewModel.getFee(), person);
        }

        return person;
    }
}
