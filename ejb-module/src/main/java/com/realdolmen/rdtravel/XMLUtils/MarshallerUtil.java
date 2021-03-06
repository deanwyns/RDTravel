package com.realdolmen.rdtravel.XMLUtils;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.util.List;

public class MarshallerUtil {
    /**
     * Unmarshal XML to JAXBWrapper and return List value.
     */
    public static <T> List<T> unmarshal(Unmarshaller unmarshaller, Class<T> clazz, XMLStreamReader xmlStreamReader) throws JAXBException {
        JAXBWrapper<T> jaxbWrapper = (JAXBWrapper<T>) unmarshaller.unmarshal(xmlStreamReader, JAXBWrapper.class).getValue();
        return jaxbWrapper.getItems();
    }

    /**
     * Wrap List in JAXBWrapper, then leverage JAXBElement to supply root element information.
     *
     * @param marshaller       the marshaller to marshal
     * @param list             the list of objects to marshal
     * @param outerElementName the name of the outer element in the xml
     * @param file             the file to be marshalled to
     * @throws JAXBException
     * @throws NullPointerException when the ID of the referenced object is null
     */
    public static File marshal(Marshaller marshaller, List<?> list, String outerElementName, File file) throws JAXBException, NullPointerException {
        QName qName = new QName(outerElementName);
        JAXBWrapper jaxbWrapper = new JAXBWrapper(list);
        JAXBElement<JAXBWrapper> jaxbElement = new JAXBElement<JAXBWrapper>(qName, JAXBWrapper.class, jaxbWrapper);
        marshaller.marshal(jaxbElement, file);
        return file;
    }
}
