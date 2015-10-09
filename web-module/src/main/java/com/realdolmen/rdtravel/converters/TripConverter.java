package com.realdolmen.rdtravel.converters;

import com.realdolmen.rdtravel.domain.Trip;
import com.realdolmen.rdtravel.persistence.TripDAO;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by JSTAX29 on 9/10/2015.
 */
@Named
@RequestScoped
public class TripConverter implements Converter {

    @Inject
    private TripDAO tripDAO;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.equals(""))
            return null;

        long id = Long.parseLong(value);
        return tripDAO.read(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null)
            return "";

        if(value instanceof Trip){
            Trip trip = (Trip) value;
            if(trip.getId() != null)
                return trip.getId().toString();
        }

        throw new ConverterException(new FacesMessage("Could not convert " + value.toString() + " to a Trip."));

    }
}
