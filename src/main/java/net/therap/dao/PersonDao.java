package net.therap.dao;

import net.therap.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Repository
public class PersonDao extends Dao<Person> {

    private static final String FIND_ALL = "FROM Person";

    public PersonDao() {
        super(Person.class);
    }

    public List<Person> findAll() {
        return entityManager.createQuery(FIND_ALL, Person.class).getResultList();
    }
}
