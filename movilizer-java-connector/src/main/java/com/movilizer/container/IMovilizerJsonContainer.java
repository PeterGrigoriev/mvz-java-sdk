package com.movilizer.container;

import com.google.gson.JsonObject;

import java.util.Date;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public interface IMovilizerJsonContainer {
    String getKey();

    String getDeviceAddress();

    String getMoveletKey();

    Date getCreationDate();

    JsonObject getData();
}
