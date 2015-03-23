package com.movilizer.util.json;

import com.google.gson.*;
import com.google.inject.Provider;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
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

    public static List<JsonElement> parseJsonArray(Reader reader) {
        if(reader == null) {
            return newArrayList();
        }

        JsonParser parser = new JsonParser();
        JsonElement parsed = parser.parse(reader);
        if(parsed.equals(JsonNull.INSTANCE)) {
            return newArrayList();
        }
        JsonArray jsonArray = parsed.getAsJsonArray();
        return toList(jsonArray);
    }

    public static List<JsonElement> parseJsonArray(Provider<Reader> readerProvider) {
        if(readerProvider == null) {
            return newArrayList();
        }
        return parseJsonArray(readerProvider.get());
    }

    public static List<JsonElement> toList(JsonArray jsonArray) {
        List<JsonElement> elements = newArrayList();
        for (JsonElement jsonElement : jsonArray) {
            elements.add(jsonElement);
        }
        return elements;
    }

    public static <T> T toJavaObject(JsonObject jsonObject, Class<T> tClass) {
        return new Gson().fromJson(jsonObject, tClass);
    }

    public static <T> List<T> parseJsonArray(Reader reader, Class<T> tClass) {
        return parseJsonArray(reader, tClass, tClass);
    }

    public static <TInterface, TClass extends TInterface> List<TInterface> parseJsonArray(Reader reader, Class<TClass> tClass, Class<TInterface> tInterface) {
        List<JsonElement> jsonElements = parseJsonArray(reader);
        return toJavaObjectList(jsonElements, tClass, tInterface);
    }

    public static <TInterface, TClass extends TInterface> List<TInterface> toJavaObjectList(List<JsonElement> jsonElements, Class<TClass> tClass, Class<TInterface> tInterface) {
        List<TInterface> javaObjects = newArrayList();
        for (JsonElement jsonElement : jsonElements) {
            javaObjects.add(toJavaObject(jsonElement.getAsJsonObject(), tClass));
        }
        return javaObjects;
    }
}
