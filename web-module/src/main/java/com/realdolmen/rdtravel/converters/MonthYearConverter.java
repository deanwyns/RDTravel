package com.realdolmen.rdtravel.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by JSTAX29 on 12/10/2015.
 */
@FacesConverter(value = "com.realdolmen.YearMonthConverter")
public class MonthYearConverter implements Converter {
    DateTimeFormatter DD_MM_YY = DateTimeFormatter.ofPattern("dd-MM-yy");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.isEmpty()) {
            return null;
        }

        value = "01-" + value;

        return LocalDate.parse(value, DD_MM_YY).plusMonths(1);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) {
            return "";
        }

        LocalDate date = (LocalDate)value;
        return date.format(DD_MM_YY);
    }
}
