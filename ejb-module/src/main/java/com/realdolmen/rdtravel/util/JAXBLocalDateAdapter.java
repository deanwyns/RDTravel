package com.realdolmen.rdtravel.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
public class JAXBLocalDateAdapter extends XmlAdapter<String, LocalDate>
{
    public LocalDate unmarshal( String date ) throws Exception
    {
        return LocalDate.parse(date);
    }

    public String marshal( LocalDate date ) throws Exception
    {
        return date.toString();
    }

}
