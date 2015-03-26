package com.movilizer.usermanagement.json;

import com.google.gson.JsonObject;
import com.movilizer.connector.IMoveletKeyWithExtension;

import java.util.Date;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class JsonReply implements IJsonReply {
    private final String deviceAddress;
    private final Date replyDate;
    private final JsonObject replyData;
    private final String key;

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


}
