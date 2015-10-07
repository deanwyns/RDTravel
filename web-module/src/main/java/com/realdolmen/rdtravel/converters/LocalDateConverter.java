package com.realdolmen.rdtravel.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by DWSAX40 on 6/10/2015.
 */
@FacesConverter(value = "com.realdolmen.LocalDateConverter")
public class LocalDateConverter implements Converter {
    DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) throws ConverterException {
        if(value == null || value.isEmpty()) {
            return null;
        }

        return LocalDate.parse(value, DD_MM_YYYY);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent component, Object value) throws ConverterException {
        if(value == null) {
            return "";
        }

        LocalDate date = (LocalDate)value;
        return date.format(DD_MM_YYYY);
    }

}
