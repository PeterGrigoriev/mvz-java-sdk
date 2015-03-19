package com.movilizer.jaxb;

import com.movilizer.pull.ReplyMovelet;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;

import static com.movilizer.util.dbc.Ensure.ensureNotNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */

public class MovilizerJaxbMarshaller extends MovilizerJaxb {
    private final Marshaller marshaller;

    public MovilizerJaxbMarshaller() {
        marshaller = loadMarshaller();
    }

    private Marshaller loadMarshaller() {
        try {
            return jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            logger.fatal(e);
            return null;
        }
    }

    private static MovilizerJaxbMarshaller instance;

    public static MovilizerJaxbMarshaller getInstance() {
        if (instance == null) {
            instance = new MovilizerJaxbMarshaller();
        }
        return instance;
    }

    public <T> String marshall(T object) {
        ensureNotNull(object, "object to marshall");

        if(object instanceof ReplyMovelet) {
            return marshall(((ReplyMovelet)object).getMovilizerReplyMovelet());
        }

        StringWriter stringWriter = new StringWriter();



        @SuppressWarnings("unchecked")
        Class<T> klass = (Class<T>) object.getClass();

        QName qName = new QName(null, getXmlTypeName(klass));
        JAXBElement<T> jaxbElement = new JAXBElement<T>(qName, klass, object);
        try {
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(stringWriter);

            marshaller.marshal(jaxbElement, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error(e);
            return "FAILED to marshall: " + e.getMessage();
        }

        return stringWriter.toString();
    }

    private <T> String getXmlTypeName(Class<T> klass) {
        XmlElement elementAnnotation = klass.getAnnotation(XmlElement.class);
        if (null != elementAnnotation) {
            return elementAnnotation.name();
        }

        XmlType typeAnnotation = klass.getAnnotation(XmlType.class);
        if (null != typeAnnotation) {
            return typeAnnotation.name();
        }

        logger.warn("Trying to serialize object without XmlElement/XmlType annotation: " + klass.getSimpleName());
        return klass.getSimpleName();

    }

}
