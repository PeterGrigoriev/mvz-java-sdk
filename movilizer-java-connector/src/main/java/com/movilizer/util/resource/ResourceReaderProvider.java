package com.movilizer.util.resource;

import com.google.inject.Provider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class ResourceReaderProvider implements Provider<Reader> {
    private final String resource;
    private final Class theClass;


    @Override
    public Reader get() {

        InputStream resourceAsStream = theClass.getResourceAsStream(resource);
        if(null == resourceAsStream) {
            throw new IllegalArgumentException("Resource [" + resource + "] does not exist");
        }

        return new InputStreamReader(resourceAsStream);
    }

    public ResourceReaderProvider(String resource) {
        this(ResourceReaderProvider.class, resource);
    }

    public ResourceReaderProvider(Class theClass, String resource) {
        this.resource = resource;
        this.theClass = theClass;
    }


    public static Provider<Reader> newResourceReaderProvider(String path) {
        return new ResourceReaderProvider(path);
    }

    public static Reader newResourceReader(String path) {
        return newResourceReaderProvider(path).get();
    }
}
