package com.movilizer.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movilizer.util.json.JsonUtils;
import junit.framework.Assert;
import org.testng.annotations.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static com.movilizer.util.json.JsonUtils.parseJsonArray;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class JsonUtilsTest {

    public static class StringAndNumber {
        private final String string;
        private final int number;

        public StringAndNumber(String string, int number) {
            this.string = string;
            this.number = number;
        }

        public String getString() {
            return string;
        }

        public int getNumber() {
            return number;
        }
    }

    @Test
    public void testToJson() throws Exception {
        StringAndNumber stringAndNumber = new StringAndNumber("A", 22);

        String json = JsonUtils.toJson(stringAndNumber);

        JsonObject parsed = (JsonObject)new JsonParser().parse(json);

        assertEquals(parsed.get("string").getAsString(), "A");
        assertEquals(parsed.get("number").getAsInt(), 22);
    }

    @Test
    public void testToStringMap() throws Exception {
        JsonObject object = new JsonObject();
        object.addProperty("a", 123);
        object.addProperty("b", "VALUE B");

        Map<String, String> map = JsonUtils.collectPrimitiveProperties(object);
        Assert.assertEquals(map.get("a"), "123");
        Assert.assertEquals(map.get("b"), "VALUE B");
    }

    @Test
    public void testParseJsonArrayForReader() {
        assertTrue(parseJsonArray((Reader) null).isEmpty());
        assertTrue(parseJsonArray(newStringReader("")).isEmpty());
        assertTrue(parseJsonArray(newStringReader("[]")).isEmpty());

    }

    private static Reader newStringReader(String string) {
        return new StringReader(string);
    }
}