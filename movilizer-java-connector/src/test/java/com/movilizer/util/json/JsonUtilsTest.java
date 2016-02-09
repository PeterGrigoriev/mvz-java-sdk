package com.movilizer.util.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import static com.movilizer.util.json.JsonUtils.collectPrimitiveProperties;
import static com.movilizer.util.json.JsonUtils.parseJsonArray;
import static org.testng.Assert.*;

public class JsonUtilsTest {

    @SuppressWarnings("unused")
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

        Map<String, String> map = collectPrimitiveProperties(object, true);
        Assert.assertEquals(map.get("a"), "123");
        Assert.assertEquals(map.get("b"), "VALUE B");
    }

    @Test
    public void testParseJsonArrayForReader() {
        assertTrue(parseJsonArray((Reader) null).isEmpty());
        assertTrue(parseJsonArray(newStringReader("")).isEmpty());
        assertTrue(parseJsonArray(newStringReader("[]")).isEmpty());
    }


    @Test
    public void testCollectPrimitiveProperties() {
        String json = "{\n" +
                "  \"id\": 23,\n" +
                "  \"nested1\": {\n" +
                "    \"id\": 555,\n" +
                "    \"email\": \"ivan.rebroff@gmail.com\",\n" +
                "    \"nested11\": {\n" +
                "      \"id\": 1,\n" +
                "      \"version\": 22\n" +
                "    }\n" +
                "  },\n" +
                "  \"nested2\": {\n" +
                "    \"id\": 222,\n" +
                "    \"email\": \"fedorivan.shalyapin@gmail.com\",\n" +
                "    \"position\": \"singer\"\n" +
                "  }\n" +
                "}";
        JsonObject jsonObject = JsonUtils.toJsonObject(json);
        Map<String, String> map = collectPrimitiveProperties(jsonObject, true);
        assertEquals(map.get("id"), "23");
        assertNull(map.get("nested1"));
        assertNull(map.get("nested2"));
        assertEquals(map.get("email"), "ivan.rebroff@gmail.com");
        assertEquals(map.get("version"), "22");
        assertEquals(map.get("position"), "singer");
    }

    private static Reader newStringReader(String string) {
        return new StringReader(string);
    }
}