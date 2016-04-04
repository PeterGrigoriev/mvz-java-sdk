package com.movilizer.util.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import static com.movilizer.util.dbc.Ensure.ensureNotNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class XMLUtils {
    public static String wrapXml(String elementName, Object... innerElements) {
        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append(elementName);
        builder.append(">");
        for (Object innerElement : innerElements) {
            if (null != innerElement) {
                builder.append(innerElement);
            }
        }
        builder.append("</");
        builder.append(elementName);
        builder.append(">");

        return builder.toString();
    }

    public static Node readXmlNode(String xml) throws ParserConfigurationException, IOException, SAXException {
        ensureNotNull(xml, "xml in readXmlNode(xml)");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new InputSource(new StringReader(xml)));

        return doc.getFirstChild();
    }

    public static void formatXml(Reader reader, Writer writer) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(reader));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        Source source = new DOMSource(document);
        transformer.transform(source, new StreamResult(writer));
    }

}
