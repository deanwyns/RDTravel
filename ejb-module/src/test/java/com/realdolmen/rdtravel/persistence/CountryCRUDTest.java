package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Country;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
public class CountryCRUDTest extends DataSetPersistenceTest {

    private CountryDAO countryDAO = new CountryDAO();
    private Country country;

    @Before
    public void initialize() {
        countryDAO.setEntityManager(entityManager());
        country = countryDAO.read(1);
        flushAndClear();
    }

    @Test
    public void testCreateCountry() {
        Country country = new Country("SomehowANewCountry", "SN", "SAN");
        assertNull(country.getId());
        countryDAO.create(country);
        flushAndClear();
        assertNotNull(country.getId());
    }

    @Test
    public void testUpdateCountry() {
        String revisionedvalue = "ANewCountryName";
        country.setName(revisionedvalue);
        countryDAO.update(country);
        flushAndClear();
        Country changedCountry = countryDAO.read(1);
        assertEquals(revisionedvalue, changedCountry.getName());
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNameIsNull() {
        country.setName(null);
        countryDAO.update(country);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNameIsEmpty() {
        country.setName("");
        countryDAO.update(country);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNameIsLargerThanMaxLength() {
        country.setName("This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name.");
        countryDAO.update(country);
        flushAndClear();
    }
}
