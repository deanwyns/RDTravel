package com.realdolmen.rdtravel.persistence;


import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Created by JSTAX29 on 4/10/2015.
 * A base implementation of the Generic DAO interface
 */
public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected Class<T> entityClass;

    protected abstract EntityManager getEntityManager();

    public GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T create(T t) {
        getEntityManager().persist(t);
        return t;
    }

    @Override
    public T read(PK id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public T update(T t) {
        return getEntityManager().merge(t);
    }

    @Override
    public void delete(T t) {
        t = getEntityManager().merge(t);
        getEntityManager().remove(t);
    }

    @Override
    public List<T> findAll() {
        return getEntityManager().createQuery("SELECT t FROM " + entityClass.getSimpleName() + " t", entityClass).getResultList();
    }
}
