package com.movilizer.util.resource;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movilizer.jaxb.MovilizerJaxbUnmarshaller;
import com.movilizer.util.json.JsonResourceReaderProvider;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ResourceUtils {
    public static <T> T fromResource(String resource, Class<T> klass) {
        Class<ResourceUtils> aClass = ResourceUtils.class;
        return fromResource(aClass, resource, klass);
    }

    public static <T> T fromResource(Class<ResourceUtils> classWithResources, String resource, Class<T> klass) {
        InputStream resourceAsStream = classWithResources.getResourceAsStream(resource);

        try {
            String xml = IOUtils.toString(resourceAsStream);
            return MovilizerJaxbUnmarshaller.getInstance().unmarshall(klass, xml);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonObject fromResource(String resourceName) {
        return fromResource(ResourceUtils.class, resourceName);
    }

    public static JsonObject fromResource(Class aClass, String resourceName) {
        return new JsonParser().parse(new JsonResourceReaderProvider(aClass, resourceName).get()).getAsJsonObject();
    }
}

