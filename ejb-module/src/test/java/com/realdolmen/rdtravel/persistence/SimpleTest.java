package com.realdolmen.rdtravel.persistence;

import com.realdolmen.rdtravel.domain.Flight;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by JSTAX29 on 2/10/2015.
 */
public class SimpleTest extends PersistenceTest {


    @Test
    public void testPersistFlightMethod(){
        entityManager().persist(new Flight());
    }
}
