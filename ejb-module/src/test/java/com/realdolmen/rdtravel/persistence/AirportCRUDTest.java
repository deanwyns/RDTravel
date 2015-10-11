package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.domain.Country;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

/**
 * Created by JSTAX29 on 8/10/2015.
 */
public class AirportCRUDTest extends DataSetPersistenceTest {
    private final String LONG_VALUE = "This is a long value. This is a long value. This is a long value. This is a long value. This is a long value. This is a long value. This is a long value. This is a long value. This is a long value. This is a long value. This is a long value. This is a long value. This is a long value.";
    private AirportDAO airportDAO = new AirportDAO();
    private Airport airport;

    @Before
    public void initialize() {
        airportDAO.setEntityManager(entityManager());
        airport = airportDAO.read(1l);
        flushAndClear();
    }


    @Test
    public void testCreateCompleteAirport() {
        Country country = entityManager().find(Country.class, 1);
        Airport airport = new Airport("An Airport", "A city", country, "IATA", "ICAO", -52.2, 68.2, 65.545, (byte) 0, 'U', "Timezone/Format");
        assertNull(airport.getId());
        airportDAO.create(airport);
        flushAndClear();
        assertNotNull(airport.getId());
    }

    @Test
    public void testUpdateAirport() {
        String revisionedvalue = "This flight just got revalued.";
        airport.setName(revisionedvalue);
        airportDAO.update(airport);
        flushAndClear();
        Airport changedAirport = airportDAO.read(1l);
        assertEquals(revisionedvalue, changedAirport.getName());
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNameIsNull() {
        airport.setName(null);
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNameIsEmpty() {
        airport.setName("");
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNameIsLargerThanMaxSize() {
        airport.setName(LONG_VALUE);
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCityIsNull() {
        airport.setCity(null);
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void CityIsEmpty() {
        airport.setCity("");
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCityIsLargerThanMaxSize() {
        airport.setCity(LONG_VALUE);
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testIataFaaIsNull() {
        airport.setIataFaa(null);
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testIataFaaIsLargerThan4() {
        airport.setIataFaa("12345");
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test
    public void testIataFaaIsOk() {
        airport.setIataFaa("1234");
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testIcaoIsNull() {
        airport.setIcao(null);
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testIcaoIsLargerThan4() {
        airport.setIcao("12345");
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testTimeZoneTzIsNull() {
        airport.setTimezoneTzFormat(null);
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testTimeZoneTzIsEmpty() {
        airport.setTimezoneTzFormat("");
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testTimeZoneTzIsLargerThanMaxSize() {
        airport.setTimezoneTzFormat(LONG_VALUE);
        airportDAO.update(airport);
        flushAndClear();
    }

    @Test
    public void testFindAllAirports(){
        assertEquals(3, airportDAO.findAll().size());
    }

    @Test
    public void testFindAirportsByCountryName(){
        assertEquals(2, airportDAO.findByCountryName("Guinea").size());
        assertEquals(1, airportDAO.findByCountryName("Greenland").size());
    }

    @Test
    public void testFindAirportsByCountryISO2(){
        assertEquals(2, airportDAO.findByCountryISO2("GN").size());
        assertEquals(1, airportDAO.findByCountryISO2("GL").size());
    }
}
