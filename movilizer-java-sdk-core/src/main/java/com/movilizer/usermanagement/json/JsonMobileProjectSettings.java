package com.movilizer.usermanagement.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.MovilizerCloudSystem;
import com.movilizer.projectmanagement.IMobileProjectSettings;

import static com.movilizer.util.json.JsonUtils.toJavaObject;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileProjectSettings implements IMobileProjectSettings{
    private final JsonObject jsonObject;

    public JsonMobileProjectSettings(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static IMobileProjectSettings fromJsonObject(JsonElement element) {
        return new JsonMobileProjectSettings(element.getAsJsonObject());
    }

    @Override
    public IMovilizerCloudSystem getMoveletCloudSystem() {
        return toJavaObject(jsonObject.getAsJsonObject("moveletCloudSystem"), MovilizerCloudSystem.class);
    }

    @Override
    public IMovilizerCloudSystem getMasterDataCloudSystem() {
        return toJavaObject(jsonObject.getAsJsonObject("masterDataCloudSystem"), MovilizerCloudSystem.class);
    }

    @Override
    public int getId() {
        return jsonObject.get("id").getAsInt();
    }

    @Override
    public String getName() {
        return jsonObject.get("name").getAsString();
    }

    @Override
    public int getVersion() {
        return jsonObject.get("version").getAsInt();
    }
}
