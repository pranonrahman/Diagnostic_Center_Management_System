package net.therap.dao;

import net.therap.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Repository
public class UserDao extends Dao<User> {

    private static final String FIND_ALL = "FROM User";
    private static final String FIND_BY_USER_NAME = "FROM User WHERE userName = :userName";

    public UserDao() {
        super(User.class);
    }

    public List<User> findAll() {
        return entityManager.createQuery(FIND_ALL, User.class).getResultList();
    }

    public User findByUserName(String userName) {
        try {
            return entityManager.createQuery(FIND_BY_USER_NAME, User.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }
}
