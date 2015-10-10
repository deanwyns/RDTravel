package com.realdolmen.rdtravel.utils;

import com.google.gson.Gson;
import com.realdolmen.rdtravel.domain.Continent;
import com.realdolmen.rdtravel.persistence.ContinentDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DWSAX40 on 9/10/2015.
 */
@Named
@RequestScoped
public class JavascriptUtil {
    @Inject private ContinentDAO continentDAO;

    public String getCountries() {
        List<Continent> continents = continentDAO.findAll();
        Map<String, Continent> continentMap = new TreeMap<>();
        continents.forEach(c -> continentMap.put(c.getISO2(), c));

        Gson gson = new Gson();
        return gson.toJson(continentMap);
    }
}
