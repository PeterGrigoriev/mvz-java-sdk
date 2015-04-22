package com.movilizer.util.json;

import com.google.common.base.Predicate;
import com.google.gson.*;
import com.google.inject.Provider;
import com.movilizer.util.collection.CollectionUtils;
import sun.awt.SunHints;

import java.io.Reader;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.movilizer.util.collection.CollectionUtils.cdr;
import static org.apache.commons.lang.StringUtils.endsWith;
import static org.apache.commons.lang.StringUtils.split;

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
        if(isNull(jsonObject)) {
            return null;
        }
        return new Gson().fromJson(jsonObject, tClass);
    }

    private static boolean isNull(JsonElement jsonElement) {
        return jsonElement == null || JsonNull.INSTANCE.equals(jsonElement);
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

    public static Predicate<JsonElement> ACCEPT_ALL = new Predicate<JsonElement>() {
        @Override
        public boolean apply(JsonElement jsonElement) {
            return true;
        }
    };

    public static JsonArray filter(JsonArray array, Predicate<JsonElement> filter) {
        JsonArray result = new JsonArray();

        for (JsonElement jsonElement : array) {
            if (filter.apply(jsonElement)) {
                result.add(jsonElement);
            }
        }
        return result;
    }

    public static Predicate<JsonElement> isJsonObjectAnd(final Predicate<JsonObject> filter) {
        return new Predicate<JsonElement>() {
            @Override
            public boolean apply(JsonElement element) {
                return element.isJsonObject() && filter.apply(element.getAsJsonObject());
            }
        };
    }

    public static Predicate<JsonObject> fieldEquals(final String fieldName, final String value) {
        return new Predicate<JsonObject>() {
            @Override
            public boolean apply(JsonObject jsonObject) {
                return jsonObject.get(fieldName).getAsString().equals(value);
            }
        };
    }

    public static String getStringValue(JsonObject jsonObject, String... propertyChain) {
        JsonElement element = get(jsonObject, propertyChain);
        if(element == null) {
            return null;
        }
        if(element.equals(JsonNull.INSTANCE)) {
            return null;
        }
        if(element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if(primitive.isString()) {
                return primitive.getAsString();
            }
        }
        return element.toString();
    }

    public static JsonElement get(JsonObject jsonObject, String... propertyChain) {
        if (propertyChain == null || propertyChain.length == 0) {
            return jsonObject;
        }

        JsonElement element = jsonObject.get(propertyChain[0]);

        if(propertyChain.length == 1) {
            return element;
        }

        return get(element.getAsJsonObject(), cdr(propertyChain));

    }

    public static JsonArray toJsonArray(Iterable<? extends Number> numbers) {
        JsonArray array = new JsonArray();
        for (Number number : numbers) {
            array.add(new JsonPrimitive(number));
        }
        return array;
    }
}