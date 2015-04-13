package com.movilizer.util.json;

import com.google.gson.*;
import com.google.inject.Provider;
import com.movilizer.masterdata.IllegalMasterdataFormatException;

import java.io.Reader;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class JsonUtils {

    private static final Gson gson = new Gson();

    public static <T> String toJson(T object) {
        return gson.toJson(object);
    }

    public static JsonObject toJsonObject(String string) {
        return gson.fromJson(string, JsonObject.class);
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
        if (reader == null) {
            return newArrayList();
        }

        JsonParser parser = new JsonParser();
        JsonElement parsed = parser.parse(reader);
        if (parsed.equals(JsonNull.INSTANCE)) {
            return newArrayList();
        }
        JsonArray jsonArray = parsed.getAsJsonArray();
        return toList(jsonArray);
    }

    public static List<JsonElement> parseJsonArray(Provider<Reader> readerProvider) {
        if (readerProvider == null) {
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

    public static JsonElement parseJsonElement(String value) {
        return new JsonParser().parse(value);
    }

    public static JsonObject fromNamesAndValues(List<String> fieldNames, List<String> values) throws IllegalArgumentException {
        if (fieldNames == null || values == null) {
            throw new IllegalArgumentException("null is not allowed here");
        }
        if (fieldNames.size() != values.size()) {
            throw new IllegalArgumentException("fieldNames.size() != values.size()");
        }
        JsonObject object = new JsonObject();
        for (int i = 0; i < fieldNames.size(); i++) {
            object.addProperty(fieldNames.get(i), values.get(i));
        }
        return object;
    }

    public static JsonObject fromMap(Map<String, String> map) {
        JsonObject object = new JsonObject();
        for (String key : map.keySet()) {
            object.addProperty(toJsonKey(key), map.get(key));
        }
        return object;
    }

    private static String toJsonKey(String key) {
        return key.replace(" ", "_");
    }
}
