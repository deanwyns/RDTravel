package com.realdolmen.rdtravel.converters;

import com.realdolmen.rdtravel.domain.Airport;
import com.realdolmen.rdtravel.persistence.AirportDAO;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@Named
@RequestScoped
public class AirportConverter implements Converter {
    @Inject
    private AirportDAO airportDAO;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value.equals("")) {
            return null;
        }

        long id = Long.parseLong(value);
        return airportDAO.read(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) {
            return "";
        }

        if(value instanceof Airport) {
            Airport airport = (Airport)value;
            if(airport.getId() != null)
                return airport.getId().toString();
        }

        throw new ConverterException(new FacesMessage("Could not convert " + value.toString() + " to an Airport"));
    }
}
