package com.realdolmen.rdtravel.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by DWSAX40 on 6/10/2015.
 */
@FacesConverter(value = "com.realdolmen.LocalDateTimeConverter")
public class LocalDateTimeConverter implements Converter {
    DateTimeFormatter DD_MM_YYYY_HH_MM = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) throws ConverterException {
        if(value == null || value.isEmpty()) {
            return new Date();
        }

        //return LocalDateTime.parse
        return LocalDateTime.parse(value, DD_MM_YYYY_HH_MM);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent component, Object value) throws ConverterException {
        LocalDateTime date = (LocalDateTime)value;
        return date.format(DD_MM_YYYY_HH_MM);
    }

}
