package com.realdolmen.rdtravel.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
public class JAXBLongAdapter extends XmlAdapter<String, Long> {
    @Override
    public Long unmarshal(String v) throws Exception {
        return Long.parseLong(v);
    }

    @Override
    public String marshal(Long v) throws Exception {
        return v.toString();
    }
}
