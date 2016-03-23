package com.movilizer.util.xml;

import org.testng.annotations.Test;
import org.w3c.dom.Node;

import java.io.StringReader;
import java.io.StringWriter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class XMLUtilsTest {
    @Test
    public void testWrapXml() throws Exception {
        String xml = XMLUtils.wrapXml("A", "<b/>", "<c/>", "</d>");

        assertTrue(xml.startsWith("<A>"));
        assertTrue(xml.endsWith("</A>"));

    }

    @Test
    public void testReadXmlNode() throws Exception {
        Node node = XMLUtils.readXmlNode("<A><B>c</B></A>");
        assertNotNull(node);
        assertEquals("A", node.getNodeName());
    }

    @Test
    public void testFormatXml() throws Exception {
        StringReader xml = new StringReader("<A><B>c</B></A>");
        StringWriter output = new StringWriter();
        XMLUtils.formatXml(xml, output);
        String formatted = output.toString();
        assertTrue(formatted.contains("<A>"));
    }
}
