package net.therap.dms.dao;

import net.therap.dms.entity.User;
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

    public User findByUserName(String userName) {
        try {
            return em.createNamedQuery("User.findByUserName", User.class)
                    .setParameter("userName", userName)
                    .getSingleResult();

        } catch (NoResultException noResultException) {
            return null;
        }
    }

    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class)
                .getResultList();
    }
}
