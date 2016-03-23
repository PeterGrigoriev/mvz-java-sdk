package com.movilizer.jaxb;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.movilizer.util.xml.XMLUtils.readXmlNode;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerJaxbUnmarshaller extends MovilizerJaxb {
    private final Unmarshaller unmarshaller;


    public <T> T unmarshall(Class<T> klass, String xml) throws ParserConfigurationException, IOException, SAXException, JAXBException {
        Node node = readXmlNode(xml);

        return unmarshall(klass, node);
    }

    public <T> T unmarshall(Class<T> klass, Node node) throws JAXBException {
        JAXBElement<T> element = unmarshaller.unmarshal(node, klass);

        return element.getValue();
    }


    public MovilizerJaxbUnmarshaller() {
        this.unmarshaller = loadUnmarshaller();
    }


    private Unmarshaller loadUnmarshaller() {
        try {
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            logger.fatal(e);
            return null;
        }
    }


    private static MovilizerJaxbUnmarshaller instance;

    public static MovilizerJaxbUnmarshaller getInstance() {
        if (instance == null) {
            instance = new MovilizerJaxbUnmarshaller();
        }
        return instance;
    }
}
