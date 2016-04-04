package com.movilizer.masterdata;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.movilizer.util.collection.CollectionUtils.printToJSON;
import static java.lang.Long.parseLong;
import static java.lang.String.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ParsedMasterdataEvent implements IParsedMasterdataEvent {
    private final Set<String> properFieldNames;
    private final Map<String, String> fieldMap;
    private final IMasterdataFieldNames fieldNames;


    public ParsedMasterdataEvent(Map<String, String> fieldMap, IMasterdataFieldNames fieldNames) {
        this.fieldMap = fieldMap;
        this.properFieldNames = new HashSet<String>(fieldMap.keySet());
        this.properFieldNames.remove(fieldNames.getEventId());
        this.properFieldNames.remove(fieldNames.getEventType());
        this.fieldNames = fieldNames;
    }


    @Override
    public String getObjectId() throws IllegalMasterdataFormatException {
        return getRequiredField(fieldNames.getObjectId());
    }

    @Override
    public String getEventId() throws IllegalMasterdataFormatException {
        return getRequiredField(fieldNames.getEventId());
    }

    @Override
    public MasterdataEventType getEventType() throws IllegalMasterdataFormatException {
        // Note: defaults to UPDATE should no field be specified
        return MasterdataEventType.fromString(getField(fieldNames.getEventType()));
    }


    @Override
    public String getRequiredField(String name) throws IllegalMasterdataFormatException {
        String value = getField(name);
        if (value == null) {
            throw new IllegalMasterdataFormatException(format("Missing required field: %s. Available fields: %s", name, printToJSON(fieldMap)));
        }
        return value;
    }

    @Override
    public String getField(String name) {
        return fieldMap.get(name);
    }

    @Override
    public String getDescription() throws IllegalMasterdataFormatException {
        return getStringFieldIfNamed(fieldNames.getDescription(), "");
    }

    private String getStringFieldIfNamed(String name, String defaultValue) throws IllegalMasterdataFormatException {
        if (name == null || "".equals(name)) {
            return defaultValue;
        }
        return getRequiredField(name);
    }

    @Override
    public String getGroup() throws IllegalMasterdataFormatException {
        return getStringFieldIfNamed(fieldNames.getGroup(), "All");
    }

    @Override
    public String getFilter1() throws IllegalMasterdataFormatException {
        return getStringFieldIfNamed(fieldNames.getFilter1(), null);
    }

    @Override
    public String getFilter2() throws IllegalMasterdataFormatException {
        return getStringFieldIfNamed(fieldNames.getFilter2(), null);
    }

    @Override
    public String getFilter3() throws IllegalMasterdataFormatException {
        return getStringFieldIfNamed(fieldNames.getFilter3(), null);
    }

    @Override
    public Long getFilter4() throws IllegalMasterdataFormatException {
        return getLongFieldIfNamed(fieldNames.getFilter4());
    }

    private Long getLongFieldIfNamed(String fieldName) throws IllegalMasterdataFormatException {
        String strValue = getStringFieldIfNamed(fieldName, null);
        if(null == strValue) {
            return null;
        }
        try {
            return parseLong(strValue);
        } catch (NumberFormatException e) {
            throw new IllegalMasterdataFormatException("Illegal number format for [" + fieldName + "]. Value is [" + strValue + "].");
        }
    }

    @Override
    public Long getFilter5() throws IllegalMasterdataFormatException {
        return getLongFieldIfNamed(fieldNames.getFilter5());
    }

    @Override
    public Long getFilter6() throws IllegalMasterdataFormatException {
        return getLongFieldIfNamed(fieldNames.getFilter6());
    }

    @Override
    public Set<String> getProperFieldNames() {
        return properFieldNames;
    }
}
