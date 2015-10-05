package com.realdolmen.rdtravel.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Created by DWSAX40 on 5/10/2015.
 */
@FacesConverter(value = "calendarDateTimeConverter")
public class CalendarDateTimeConverter implements Converter {
    DateTimeFormatter DD_MM_YYYY_HH_MM = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) throws ConverterException {

        if( value==null || value.isEmpty()) {
            return new Date();
        }

        return LocalDateTime.parse(value, DD_MM_YYYY_HH_MM);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent component, Object value) throws ConverterException {
        LocalDateTime date = (LocalDateTime)value;
        return date.format(DD_MM_YYYY_HH_MM);
    }

}
