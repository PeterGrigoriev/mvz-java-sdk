package com.movilizer.container;

import com.google.gson.JsonObject;
import com.movilitas.movilizer.v12.MovilizerGenericDataContainer;
import com.movilitas.movilizer.v12.MovilizerGenericDataContainerEntry;
import com.movilitas.movilizer.v12.MovilizerGenericUploadDataContainer;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

import static com.movilizer.util.datetime.DateTimeUtils.asDate;
import static com.movilizer.util.json.JsonUtils.toJsonObject;


/**
 * @author Peter.Grigoriev@gmail.com.
 *
 * This class is used to consume HTML5 (cordova) movelet results.
 * In JavaScript we store the result of the mobile app in a global variable using
 * movilizer.writeGlobalVariable("result", JSON.stringify(resultObject)).
 * Then, in MEL we use writeContainer("someName", jsonString) to send the result to the cloud.
 * Thus, MovilizerJsonContainer expects the one and only value in the container, which is a result of JSON.stringify()
 */
public class MovilizerJsonContainer implements IMovilizerJsonContainer {
    private final String key;
    private final String deviceAddress;
    private final String moveletKey;
    private final Date creationDate;
    private final JsonObject data;

    public MovilizerJsonContainer(String key, String deviceAddress, String moveletKey, Date creationDate, JsonObject data) {

        this.key = key;
        this.deviceAddress = deviceAddress;
        this.moveletKey = moveletKey;
        this.creationDate = creationDate;
        this.data = data;
    }

    public static IMovilizerJsonContainer fromUploadDataContainer(MovilizerGenericUploadDataContainer container) {
        String deviceAddress = container.getDeviceAddress();
        String key = container.getKey();
        String moveletKey = container.getMoveletKey();

        XMLGregorianCalendar creationTimestamp = container.getCreationTimestamp();
        Date creationDate = asDate(creationTimestamp);

        MovilizerGenericDataContainer data = container.getData();
        if(data == null) {
            return null;
        }
        List<MovilizerGenericDataContainerEntry> entries = data.getEntry();
        if(entries == null || entries.isEmpty()) {
            return null;
        }

        String value = entries.get(0).getValstr();
        JsonObject jsonObject = toJsonObject(value);
        return new MovilizerJsonContainer(key, deviceAddress, moveletKey, creationDate, jsonObject);

    }


    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDeviceAddress() {
        return deviceAddress;
    }

    @Override
    public String getMoveletKey() {
        return moveletKey;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public JsonObject getData() {
        return data;
    }
}
