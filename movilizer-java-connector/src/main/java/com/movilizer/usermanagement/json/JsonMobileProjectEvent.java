package com.movilizer.usermanagement.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.projectmanagement.IMobileProjectEvent;
import com.movilizer.projectmanagement.MobileProjectDescription;
import com.movilizer.projectmanagement.MobileProjectEventType;

/**
 * @author philippe.guillamet@gdfsuez.com
 */
public class JsonMobileProjectEvent implements IMobileProjectEvent {

    private JsonObject jsonObject;

    private JsonMobileProjectEvent(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static JsonMobileProjectEvent createJsonMobileProjectEvent(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return new JsonMobileProjectEvent(jsonObject);
    }

    @Override
    public IMobileProjectDescription getProjectDescription() {
        String name = jsonObject.getAsJsonObject("project").get("name").getAsString();
        int version = jsonObject.getAsJsonObject("project").get("id").getAsInt();
        return new MobileProjectDescription(name, version);
    }

    @Override
    public MobileProjectEventType getType() {
        String type = jsonObject.get("type").getAsString();
        return MobileProjectEventType.fromString(type);
    }

    @Override
    public int getId() {
      return jsonObject.get("id").getAsInt();
    }
}
