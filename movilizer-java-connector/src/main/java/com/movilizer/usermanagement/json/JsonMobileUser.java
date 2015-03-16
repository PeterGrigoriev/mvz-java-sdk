package com.movilizer.usermanagement.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MovilizerUserInvitationMethod;
import com.movilizer.usermanagement.MovilizerUserStatus;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.movilizer.util.string.StringUtils.emailToDeviceAddress;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
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
        return jsonObject.get("name").getAsString();
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
        return jsonObject.getAsString();
    }

    @Override
    public Set<String> getFieldNames() {
        Set<String> res = newHashSet();
        for (Map.Entry<String, JsonElement> elementEntry : jsonObject.entrySet()) {
            res.add(elementEntry.getKey());
        }
        return res;
    }
}
