package com.movilizer.usermanagement.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.movilizer.assignmentmanagement.IMobileAssignmentEvent;
import com.movilizer.assignmentmanagement.MobileAssignmentEventType;
import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.projectmanagement.MobileProjectDescription;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.util.json.JsonUtils;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileAssignmentEvent implements IMobileAssignmentEvent {
    private JsonObject jsonObject;


    private JsonMobileAssignmentEvent(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static IMobileAssignmentEvent fromJsonObject(JsonElement element) {
        return new JsonMobileAssignmentEvent(element.getAsJsonObject());
    }

    @Override
    public MobileAssignmentEventType getType() {
        JsonElement eventType = jsonObject.get("eventType");
        return MobileAssignmentEventType.fromString(eventType.getAsString());
    }

    @Override
    public IMovilizerUser getUser() {
        return JsonMobileUser.fromJsonObject(getAssignment().getAsJsonObject("user"));
    }

    private JsonObject getAssignment() {
        JsonObject assignment = jsonObject.getAsJsonObject("assignment");

        return assignment;
    }

    @Override
    public IMobileProjectDescription getProjectDescription() {
        return JsonUtils.toJavaObject(getProject(), MobileProjectDescription.class);
    }

    private JsonObject getProject() {
        return toJsonObject(getAssignment().get("project"));
    }

    public static JsonObject toJsonObject(JsonElement element) {
        if(JsonNull.INSTANCE.equals(element)) {
            return null;
        }
        return element.getAsJsonObject();
    }

    @Override
    public int getId() {
        return jsonObject.get("id").getAsInt();
    }
}
