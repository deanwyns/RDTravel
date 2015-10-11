package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Customer;
import com.realdolmen.rdtravel.domain.User;
import com.realdolmen.rdtravel.util.PasswordHash;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by JSTAX29 on 11/10/2015.
 */
public class UserCRUDTest extends DataSetPersistenceTest {

    private UserDAO userDAO;

    @Before
    public void initialize(){
        userDAO = new UserDAO();
        userDAO.setEntityManager(entityManager());
    }

    @Test
    public void testFindByEmailFindsUser(){
        assertNotNull(userDAO.findByEmail("johnny@info.com"));
    }

    @Test
    public void testFindByEmailFindsUserDifferentCasing(){
        assertNotNull(userDAO.findByEmail("johNny@inFo.cOm"));
    }

    @Test(expected = NoResultException.class)
    public void testFindByEmailFindsNoUser(){
        userDAO.findByEmail("notfound@mail.com");
    }

    @Test
    public void testCorrectPassword() throws InvalidKeySpecException, NoSuchAlgorithmException {
        User customer = new Customer("Jim", "Jack", "Jim@Jack.com", "p@55word");

        assertTrue(PasswordHash.validatePassword("p@55word", customer.getPassword()));
    }

    @Test
    public void testIncorrectPassword() throws InvalidKeySpecException, NoSuchAlgorithmException {
        User customer = new Customer("Jim", "Jack", "Jim@Jack.com", "p@55word");

        assertFalse(PasswordHash.validatePassword("incorrect", customer.getPassword()));
    }
}
