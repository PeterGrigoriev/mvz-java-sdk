package com.movilizer.masterdata;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MasterdataXmlSettingsTest {

    @Test
    public void testConstructorForNonReference() throws Exception {
        MasterdataXmlSettings settings = new MasterdataXmlSettings("sub", "poo", 1000, 2,
                new MasterdataFieldNames("gro", "123", "desc"));
        assertEquals(settings.getLimit(), 1000);
        assertEquals(settings.getSubscriber(), "sub");
        assertEquals(settings.getNumberOfLoops(), 2);
        assertFalse(settings.isReference());
    }

    @Test
    public void testConstructorForReference() throws Exception {
        MasterdataXmlSettings settings = new MasterdataXmlSettings("sub", "poo", 1000, 2,
                new MasterdataFieldNames("gro", "123", "desc"), "myTargetPool");
        assertEquals(settings.getLimit(), 1000);
        assertEquals(settings.getSubscriber(), "sub");
        assertEquals(settings.getNumberOfLoops(), 2);
        assertTrue(settings.isReference());
        assertEquals(settings.getTargetPool(), "myTargetPool");
    }

    public static final String XML_NON_REFERENCE = "            <xml>\n" +
            "                <subscriber>eMove</subscriber>\n" +
            "                <pool>performance_measure_eMove</pool>\n" +
            "                <limit>100</limit>\n" +
            "                <loops>1</loops>\n" +
            "                <fields>\n" +
            "                    <object-id>OBJECT_KEY</object-id>\n" +
            "                    <event-type>OBJECT_FUNCTION</event-type>\n" +
            "                    <description>LABEL</description>\n" +
            "                </fields>\n" +
            "            </xml>\n";


    public static final String XML_WITH_REFERENCE = "<xml>\n" +
            "                <subscriber>FRI</subscriber>\n" +
            "                <pool>routeMvl</pool>\n" +
            "                <target-pool>installation_eMove</target-pool>\n" +
            "                <limit>100</limit>\n" +
            "                <loops>1</loops>\n" +
            "                <fields>\n" +
            "                    <object-id>INS_CD</object-id>\n" +
            "                    <event-type>OBJECT_FUNCTION</event-type>\n" +
            "                    <group>PER_NUM</group>\n" +
            "                </fields>\n" +
            "            </xml>\n";

    @Test
    public void testFromXmlForReferenceSetting() throws ConfigurationException {
        IMasterdataXmlSetting setting = MasterdataXmlSettings.fromXml(XML_WITH_REFERENCE);
        assertTrue(setting.isReference());
        assertEquals(setting.getPool(), "routeMvl");
        assertEquals(setting.getTargetPool(), "installation_eMove");
        assertEquals(setting.getNumberOfLoops(), 1);
        assertEquals(setting.getLimit(), 100);
        IMasterdataFieldNames fieldNames = setting.getFieldNames();
        assertEquals(fieldNames.getObjectId(), "INS_CD");
        assertEquals(fieldNames.getGroup(), "PER_NUM");
    }


    @Test
    public void testFromXmlForNormalSetting() throws ConfigurationException {
        IMasterdataXmlSetting setting = MasterdataXmlSettings.fromXml(XML_NON_REFERENCE);
        assertFalse(setting.isReference());
        assertEquals(setting.getPool(), "performance_measure_eMove");
        assertNull(setting.getTargetPool());
        assertEquals(setting.getLimit(), 100);
        assertEquals(setting.getNumberOfLoops(), 1);
        IMasterdataFieldNames fieldNames = setting.getFieldNames();
        assertEquals(fieldNames.getObjectId(), "OBJECT_KEY");
        assertEquals(fieldNames.getEventType(), "OBJECT_FUNCTION");
        assertEquals(fieldNames.getDescription(), "LABEL");
    }


}