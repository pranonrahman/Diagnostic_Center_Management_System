package net.therap.dao;

import net.therap.entity.Persistent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Dao<T extends Persistent> {

    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager em;

    public Dao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findById(long id) {
        return em.find(clazz, id);
    }

    public T saveOrUpdate(T object) {
        if(object.isNew()) {
            em.persist(object);
        } else {
            object = em.merge(object);
        }

        em.flush();
        return object;
    }

    public void delete(T object) {
        em.remove(object);
    }
}
