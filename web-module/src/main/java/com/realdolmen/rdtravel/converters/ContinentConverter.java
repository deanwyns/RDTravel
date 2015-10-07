package com.realdolmen.rdtravel.converters;

import com.realdolmen.rdtravel.domain.Continent;
import com.realdolmen.rdtravel.persistence.ContinentDAO;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by DWSAX40 on 7/10/2015.
 */
@Named
@RequestScoped
public class ContinentConverter implements Converter {
    @Inject private ContinentDAO continentDAO;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value.equals("")) {
            return null;
        }

        int id = Integer.parseInt(value);
        return continentDAO.read(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) {
            return "";
        }

        if(value instanceof Continent) {
            Continent continent = (Continent)value;
            if(continent.getId() != null)
                return continent.getId().toString();
        }

        throw new ConverterException(new FacesMessage("Could not convert " + value.toString() + " to a Continent"));
    }
}
