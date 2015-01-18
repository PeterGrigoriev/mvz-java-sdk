package com.movilizer.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class JsonUtils {

    public static <T> String toJson(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * @param jsonObject - object to collect top-level properties of
     * @return map of top-level (primitive) properties
     */
    public static Map<String, String> collectPrimitiveProperties(JsonObject jsonObject) {
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        Map<String, String> stringMap = newHashMap();
        for (Map.Entry<String, JsonElement> entry : entries) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if (value.isJsonPrimitive()) {
                stringMap.put(key, value.getAsString());
            }
        }
        return stringMap;
    }
}
