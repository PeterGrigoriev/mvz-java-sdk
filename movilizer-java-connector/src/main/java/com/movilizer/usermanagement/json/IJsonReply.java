package com.movilizer.usermanagement.json;

import com.google.gson.JsonObject;

import java.util.Date;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IJsonReply {
    String getKey();
    String getDeviceAddress();
    Date getReplyDate();
    JsonObject getReplyData();
}
