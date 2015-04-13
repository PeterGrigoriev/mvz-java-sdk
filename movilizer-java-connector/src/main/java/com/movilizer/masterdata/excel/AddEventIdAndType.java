package com.movilizer.masterdata.excel;

import com.google.gson.JsonObject;
import com.movilizer.push.EventType;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class AddEventIdAndType implements Operation2<JsonObject, Integer> {

    private final EventType eventType;
    private final String idFieldName;

    public AddEventIdAndType(EventType eventType, String idFieldName) {
        this.eventType = eventType;
        this.idFieldName = idFieldName;
    }

    public void apply(JsonObject jsonObject, Integer rowNumber) {
        jsonObject.addProperty("eventId", rowNumber);
        jsonObject.addProperty("eventType", eventType.toString());
        if(!jsonObject.has(idFieldName)) {
            jsonObject.addProperty("id", rowNumber);
        }
    }
}
