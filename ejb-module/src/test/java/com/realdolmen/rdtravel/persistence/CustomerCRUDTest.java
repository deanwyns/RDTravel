package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Administrator;
import com.realdolmen.rdtravel.domain.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.io.FileNotFoundException;

/**
 * Created by JSTAX29 on 5/10/2015.
 */
public class CustomerCRUDTest extends DataSetPersistenceTest {

    private CustomerDAO customerDAO = new CustomerDAO();
    private Customer customer;

    @Before
    public void initialize() {
        customerDAO.setEntityManager(entityManager());
        customer = customerDAO.read(1l);
    }

    @Test
    public void testCreateCustomer() throws FileNotFoundException {
        Customer customer = new Customer("Jack", "Smith", "jack@smith.com", "p@55word");
        customerDAO.create(customer);
        flushAndClear();
        assertNotNull(customer.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomerFirstNameNull() {
        customer.setFirstName(null);
        customerDAO.create(customer);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomerLastNameNull() {
        customer.setLastName(null);
        customerDAO.create(customer);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomerEmailNull() {
        customer.setEmail(null);
        customerDAO.create(customer);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomerEmailInvalid() {
        customer.setEmail("invalid");
        customerDAO.create(customer);
        flushAndClear();
    }

    @Test(expected = PersistenceException.class)
    public void testCreateCustomerDuplicateEmail() {
        entityManager().persist(customer);
        Customer secondCustomer = new Customer("Johnny", "Johnson", "johnny@info.com", "password");
        customerDAO.create(secondCustomer);
        flushAndClear();
    }

    @Test(expected = PersistenceException.class)
    public void testCreateCustomerDuplicateEmailNotEqualCasing() {
        entityManager().persist(customer);
        Customer secondCustomer = new Customer("Johnny", "Johnson", "Johnny@Info.cOm", "password");
        customerDAO.create(secondCustomer);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomerPasswordNull() {
        customer.setPassword(null);
        customerDAO.create(customer);
        flushAndClear();

    }

    @Test
    public void testPasswordIsHashed() {
        String password = "P@55Word";
        customer.setPassword(password);
        Assert.assertNotEquals(password, customer.getPassword());
    }

    @Test
    public void testCustomerCanBeDeleted() {
        customerDAO.delete(customer);
        flushAndClear();
        Assert.assertNull(entityManager().find(Customer.class, 1l));
    }

    @Test
    public void testCustomerCanBeEdited() {
        String newEmail = "new@email.com";
        customer.setEmail(newEmail);
        customerDAO.update(customer);
        flushAndClear();
        Customer updatedCustomer = customerDAO.read(1l);
        assertEquals(newEmail, updatedCustomer.getEmail());
    }
}
