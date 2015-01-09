package com.movilizer.masterdata;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MasterdataFieldNamesTest {

    private MasterdataFieldNames fieldNames;

    @BeforeMethod
    public void setUp() throws Exception {
        fieldNames = new MasterdataFieldNames("groupOne", "idOne", "descriptionOne");

    }

    @Test
    public void testSetEventId() throws Exception {
        fieldNames.setEventId("idTwo");
        assertEquals(fieldNames.getEventId(), "idTwo");
    }

    @Test
    public void testSetEventType() throws Exception {
        fieldNames.setEventType("CREATE");
        assertEquals(fieldNames.getEventType(), "CREATE");
    }

    @Test
    public void testSetGroup() throws Exception {
        fieldNames.setGroup("groupTwo");
        assertEquals(fieldNames.getGroup(), "groupTwo");

    }

    @Test
    public void testSetObjectId() throws Exception {
        fieldNames.setObjectId("idTwo");
        assertEquals(fieldNames.getObjectId(), "idTwo");
    }

    @Test
    public void testSetFilter1() throws Exception {
        fieldNames.setFilter1("someFilter1");
        assertEquals(fieldNames.getFilter1(), "someFilter1");
    }

    @Test
    public void testSetFilter2() throws Exception {
        fieldNames.setFilter2("someFilter2");
        assertEquals(fieldNames.getFilter2(), "someFilter2");
    }

    @Test
    public void testSetFilter3() throws Exception {
        fieldNames.setFilter3("someFilter3");
        assertEquals(fieldNames.getFilter3(), "someFilter3");
    }

    @Test
    public void testSetFilter4() throws Exception {
        fieldNames.setFilter4("someFilter4");
        assertEquals(fieldNames.getFilter4(), "someFilter4");
    }

    @Test
    public void testSetFilter5() throws Exception {
        fieldNames.setFilter5("someFilter5");
        assertEquals(fieldNames.getFilter5(), "someFilter5");
    }

    @Test
    public void testSetFilter6() throws Exception {
        fieldNames.setFilter6("someFilter6");
        assertEquals(fieldNames.getFilter6(), "someFilter6");
    }

    @Test
    public void testSetDescription() throws Exception {
        fieldNames.setDescription("descriptionTwo");
        assertEquals(fieldNames.getDescription(), "descriptionTwo");
    }
}