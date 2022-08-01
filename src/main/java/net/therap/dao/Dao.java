package net.therap.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public T findById(Long id) {
        return entityManager.find(clazz, id);
    }

    public T save(T object) {
        entityManager.persist(object);
        entityManager.flush();
        return object;
    }

    public T update(T object) {
        object = entityManager.merge(object);
        entityManager.flush();
        return object;
    }

    public void delete(T object) {
        entityManager.remove(object);
    }
}
