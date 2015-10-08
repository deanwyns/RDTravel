package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by DWSAX40 on 8/10/2015.
 */
@Named
@RequestScoped
public class UserDAO extends GenericDaoImpl<User, Long> {
    @PersistenceContext
    private EntityManager em;

    public UserDAO() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public User findByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_EMAIL, User.class).setParameter("email", email);

        return query.getSingleResult();
    }
}
