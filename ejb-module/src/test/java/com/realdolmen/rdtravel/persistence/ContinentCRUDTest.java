package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Continent;
import com.realdolmen.rdtravel.domain.Country;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
public class ContinentCRUDTest extends DataSetPersistenceTest {

    private ContinentDAO continentDAO = new ContinentDAO();
    private Continent continent;

    @Before
    public void initialize() {
        continentDAO.setEntityManager(entityManager());
        continent = continentDAO.read(1);
        flushAndClear();
    }

    @Test
    public void testCreateContinent() {
        Country country1 = new Country("Some new country 1", "SN", "SN1");
        Country country2 = new Country("Some new country 2", "SN", "SN2");
        Set<Country> countrySet = new HashSet<>(Arrays.asList(country1, country2));
        Continent continent = new Continent("ANewContinent", "AN", countrySet);

        assertNull(continent.getId());
        continentDAO.create(continent);
        flushAndClear();
        assertNotNull(continent.getId());
    }

    @Test
    public void testUpdateContinent() {
        String revisionedvalue = "ANewContinentName";
        continent.setName(revisionedvalue);
        continentDAO.update(continent);
        flushAndClear();
        Continent changedContinent = continentDAO.read(1);
        assertEquals(revisionedvalue, changedContinent.getName());
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNameIsNull() {
        continent.setName(null);
        continentDAO.update(continent);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNameIsEmpty() {
        continent.setName("");
        continentDAO.update(continent);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNameIsLargerThanMaxLength() {
        continent.setName("This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name. This is a long name.");
        continentDAO.update(continent);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testContinentWithCountriesNull(){
        continent.setCountries(null);
        continentDAO.update(continent);
        flushAndClear();
    }

    @Test
    public void testFindContinentByISO2(){
        Continent NA = entityManager().find(Continent.class, 1);
        assertEquals(NA, continentDAO.findByISO2("NA"));

        Continent AF = entityManager().find(Continent.class, 5);
        assertEquals(AF, continentDAO.findByISO2("AF"));
    }

    @Test
    public void testFindAllContinents(){
        assertEquals(2, continentDAO.findAll().size());
    }
}
