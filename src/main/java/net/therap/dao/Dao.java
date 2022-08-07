package net.therap.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Dao<T> {

    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    public Dao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findById(long id) {
        return entityManager.find(clazz, id);
    }

    public T saveOrUpdate(T object) {
        object = entityManager.merge(object);
        entityManager.flush();
        return object;
    }

    public void delete(T object) {
        entityManager.remove(object);
    }
}
