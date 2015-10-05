package com.realdolmen.rdtravel.converters;

import com.realdolmen.rdtravel.domain.Flight;
import com.realdolmen.rdtravel.persistence.FlightDAO;

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
public class FlightConverter implements Converter {
    @Inject
    private FlightDAO flightDAO;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value.equals("")) {
            return null;
        }

        long id = Long.parseLong(value);
        return flightDAO.read(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) {
            return "";
        }

        if(value instanceof Flight) {
            Flight flight = (Flight)value;
            return flight.getId().toString();
        }

        throw new ConverterException(new FacesMessage("Could not convert " + value.toString() + " to a Flight"));
    }
}
