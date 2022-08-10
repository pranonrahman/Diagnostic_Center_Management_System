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

    public UserDao() {
        super(User.class);
    }

    public List<User> findAll() {
        return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public User findByUserName(String userName) {
        try {
            return entityManager.createNamedQuery("User.findByUserName", User.class)
                    .setParameter("userName", userName)
                    .getSingleResult();

        } catch (NoResultException noResultException) {
            return null;
        }
    }
}
