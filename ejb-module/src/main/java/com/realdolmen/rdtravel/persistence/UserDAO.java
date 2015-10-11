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

    /**
     * Returns the user based on his email.
     * @param email the email of the user to be found
     * @return the user found by his email
     * @throws javax.persistence.NoResultException when no user is found
     */
    public User findByEmail(String email) {
        TypedQuery<User> query = getEntityManager().createNamedQuery(User.FIND_BY_EMAIL, User.class).setParameter("email", email);
        return query.getSingleResult();
    }
}
