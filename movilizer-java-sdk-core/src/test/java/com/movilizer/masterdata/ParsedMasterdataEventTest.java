package com.movilizer.masterdata;

import org.testng.annotations.Test;

import java.util.HashMap;

public class ParsedMasterdataEventTest {

    @Test(expectedExceptions =  IllegalMasterdataFormatException.class)
    public void testGetRequiredField() throws IllegalMasterdataFormatException {

        HashMap<String, String> fieldMap = new HashMap<String, String>();

        MasterdataFieldNames fieldNames = new MasterdataFieldNames("group", "id", "field_one");
        ParsedMasterdataEvent event = new ParsedMasterdataEvent(fieldMap, fieldNames);
        event.getRequiredField("foo");
    }
}