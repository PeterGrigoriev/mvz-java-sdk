package com.movilizer.masterdata.json;

import com.google.gson.JsonObject;
import com.movilizer.push.EventType;
import com.movilizer.util.functional.Operation2;

import static com.movilizer.masterdata.json.JsonDefaultFieldNames.*;

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
        jsonObject.addProperty(EVENT_ID, rowNumber);
        jsonObject.addProperty(EVENT_TYPE, eventType.toString());
        if(!jsonObject.has(idFieldName)) {
            jsonObject.addProperty(OBJECT_ID, rowNumber);
        }
    }
}
