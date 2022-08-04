package net.therap.dao;

import net.therap.model.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Repository
public class PersonDao extends Dao<Person> {

    private static final String FIND_ALL = "FROM Person";
    private static final String FIND_BY_USER_NAME = "FROM Person WHERE userName = :userName";

    public PersonDao() {
        super(Person.class);
    }

    public List<Person> findAll() {
        return entityManager.createQuery(FIND_ALL, Person.class).getResultList();
    }

    public Person findByUserName(String userName) {
        try {
            return entityManager.createQuery(FIND_BY_USER_NAME, Person.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }
}
