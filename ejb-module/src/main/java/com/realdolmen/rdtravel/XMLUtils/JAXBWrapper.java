package com.realdolmen.rdtravel.XMLUtils;

import javax.xml.bind.annotation.XmlAnyElement;
import java.util.ArrayList;
import java.util.List;

public class JAXBWrapper<T> {

    private List<T> items;

    public JAXBWrapper() {
        items = new ArrayList<T>();
    }

    public JAXBWrapper(List<T> items) {
        this.items = items;
    }

    @XmlAnyElement(lax=true)
    public List<T> getItems() {
        return items;
    }
}