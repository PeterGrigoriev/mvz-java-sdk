package com.movilizer.masterdata;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.inject.Singleton;
import com.movilizer.util.json.JsonUtils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MasterdataJsonReader extends MasterdataReader implements IMasterdataJsonReader {
    @Override
    public List<IParsedMasterdataEvent> parse(Reader reader, IMasterdataXmlSetting settings) throws XMLStreamException, IOException {
        JsonReader jsonReader = new JsonReader(reader);
        try {
            return readMasterdataEvents(jsonReader, settings);
        } finally {
            jsonReader.close();
        }
    }

    public List<IParsedMasterdataEvent> readMasterdataEvents(JsonReader reader, IMasterdataXmlSetting settings) throws IOException {
        List<IParsedMasterdataEvent> events = newArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            events.add(readEvent(reader, settings));
        }
        reader.endArray();
        return events;
    }

    private IParsedMasterdataEvent readEvent(JsonReader reader, IMasterdataXmlSetting settings) {
        JsonElement parse = new JsonParser().parse(reader);
        JsonObject jsonObject = parse.getAsJsonObject();
        return toMasterdataEvent(jsonObject, settings);
    }

    private IParsedMasterdataEvent toMasterdataEvent(JsonObject jsonObject, IMasterdataXmlSetting settings) {
        return new ParsedMasterdataEvent(getFieldMap(jsonObject), settings.getFieldNames());
    }

    private Map<String, String> getFieldMap(JsonObject jsonObject) {
        Map<String, String> primitiveProperties = JsonUtils.collectPrimitiveProperties(jsonObject);
        if(jsonObject.has("fields")) {
            primitiveProperties.putAll(getFieldMap(jsonObject.getAsJsonObject("fields")));
        }
        return primitiveProperties;
    }
}
