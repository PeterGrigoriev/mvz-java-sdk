package com.movilizer.usermanagement.json;

import com.google.gson.JsonObject;
import com.movilizer.connector.IMoveletKeyWithExtension;

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
