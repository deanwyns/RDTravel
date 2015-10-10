package com.realdolmen.rdtravel.XMLUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
public class JAXBLongAdapter extends XmlAdapter<String, Long> {
    @Override
    public Long unmarshal(String v) throws Exception {
        if(v != null && !v.isEmpty())
            return Long.parseLong(v);

        return null;
    }

    @Override
    public String marshal(Long v) throws Exception {
        return v != null ? v.toString() : null;
    }
}
