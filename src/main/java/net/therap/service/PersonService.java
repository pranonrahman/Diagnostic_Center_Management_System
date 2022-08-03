package net.therap.service;

import net.therap.dao.PersonDao;
import net.therap.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    public List<Person> findAll() {
        return personDao.findAll();
    }

    public Person findById(Long id) {
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
}
