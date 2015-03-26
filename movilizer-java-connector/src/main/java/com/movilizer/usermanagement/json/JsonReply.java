package com.movilizer.usermanagement.json;

import com.google.gson.JsonObject;

import java.util.Date;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class JsonReply implements IJsonReply {
    private final String key;
    private final String deviceAddress;
    private final Date replyDate;
    private final JsonObject replyData;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDeviceAddress() {
        return deviceAddress;
    }

    @Override
    public Date getReplyDate() {
        return replyDate;
    }

    @Override
    public JsonObject getReplyData() {
        return replyData;
    }

    public JsonReply(String key, String deviceAddress, Date replyDate, JsonObject replyData) {
        this.key = key;
        this.deviceAddress = deviceAddress;
        this.replyDate = replyDate;
        this.replyData = replyData;
    }


    @Override
    public String toString() {
        return "{" +
                "key='" + key + '\'' +
                ", deviceAddress='" + deviceAddress + '\'' +
                ", replyDate=" + replyDate +
                ", replyData=" + replyData +
                '}';
    }
}
