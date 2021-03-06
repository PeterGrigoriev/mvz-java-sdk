package com.movilizer.usermanagement.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MovilizerUserInvitationMethod;
import com.movilizer.usermanagement.MovilizerUserStatus;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.movilizer.util.json.JsonUtils.getStringValue;
import static com.movilizer.util.string.StringUtils.emailToDeviceAddress;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang.StringUtils.split;

public class JsonMobileUser implements IMovilizerUser {
    private JsonObject jsonObject;

    private JsonMobileUser(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static IMovilizerUser fromJsonObject(JsonElement element) {
        return new JsonMobileUser(element.getAsJsonObject());
    }

    @Override
    public String getEmail() {
        return jsonObject.get("email").getAsString();
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public int getEmployeeNumber() {
        return jsonObject.get("employeeNumber").getAsInt();
    }

    @Override
    public String getName() {
        JsonElement name = jsonObject.get("name");
        if (name != null) {
            return name.getAsString();
        }

        String firstName = jsonObject.get("firstName").getAsString();
        String lastName = jsonObject.get("lastName").getAsString();
        return format("{0} {1}", firstName, lastName);
    }

    @Override
    public MovilizerUserInvitationMethod getInvitationMethod() {
        return MovilizerUserInvitationMethod.EMAIL;
    }

    @Override
    public String getDeviceAddress() {
        return emailToDeviceAddress(getEmail());
    }

    @Override
    public MovilizerUserStatus getStatus() {
        return MovilizerUserStatus.EXISTING;
    }

    @Override
    public void setStatus(MovilizerUserStatus status) {

    }

    @Override
    public String get(String fieldName) {
        return getStringValue(jsonObject, split(fieldName, "."));
    }


    @Override
    public Set<String> getFieldNames() {
        Set<String> fieldNames = newHashSet();
        for (Map.Entry<String, JsonElement> elementEntry : jsonObject.entrySet()) {
            fieldNames.add(elementEntry.getKey());
        }
        return fieldNames;
    }
}
