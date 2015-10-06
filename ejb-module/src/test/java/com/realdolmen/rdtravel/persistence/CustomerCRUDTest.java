package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

/**
 * Created by JSTAX29 on 5/10/2015.
 */
public class CustomerCRUDTest extends PersistenceTest {

    private Customer customer;

    @Before
    public void initialize() {
        super.initialize();
        customer = new Customer("John", "Smith", "John.Smith@Mail.com", "P@ssword");
    }

    @Test
    public void testCreateCustomer() {
        entityManager().persist(customer);
        assertNotNull(customer.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomerFirstNameNull() {
        customer.setFirstName(null);
        entityManager().persist(customer);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomerLastNameNull() {
        customer.setLastName(null);
        entityManager().persist(customer);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomerEmailNull() {
        customer.setEmail(null);
        entityManager().persist(customer);
    }

    @Test(expected = ConstraintViolationException.class)
     public void testCreateCustomerEmailInvalid() {
        customer.setEmail("invalid");
        entityManager().persist(customer);
    }

    @Test(expected = PersistenceException.class)
    public void testCreateCustomerDuplicateEmail() {
        entityManager().persist(customer);
        Customer secondCustomer = new Customer("John", "Smith 2nd", "John.Smith@Mail.com", "SomePW");
        entityManager().persist(secondCustomer);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomerPasswordNull() {
        customer.setPassword(null);
        entityManager().persist(customer);
    }

    @Test
    public void testPasswordIsHashed(){
        String password = "P@55Word";
        customer.setPassword(password);
        Assert.assertNotEquals(password, customer.getPassword());
    }
}
