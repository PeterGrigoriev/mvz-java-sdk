package com.movilizer.masterdata;

import java.util.Set;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IParsedMasterdataEvent {
    String getObjectId() throws IllegalMasterdataFormatException;

    String getEventId() throws IllegalMasterdataFormatException;

    MasterdataEventType getEventType() throws IllegalMasterdataFormatException;

    String getRequiredField(String name) throws IllegalMasterdataFormatException;

    String getField(String name);

    String getDescription() throws IllegalMasterdataFormatException;

    String getGroup() throws IllegalMasterdataFormatException;

    String getFilter1() throws IllegalMasterdataFormatException;

    String getFilter2() throws IllegalMasterdataFormatException;

    String getFilter3() throws IllegalMasterdataFormatException;

    Long getFilter4() throws IllegalMasterdataFormatException;

    Long getFilter5() throws IllegalMasterdataFormatException;

    Long getFilter6() throws IllegalMasterdataFormatException;

    Set<String> getProperFieldNames();
}
