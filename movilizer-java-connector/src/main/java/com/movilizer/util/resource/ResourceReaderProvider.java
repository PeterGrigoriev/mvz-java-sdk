package com.movilizer.util.resource;

import com.google.inject.Provider;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class ResourceReaderProvider implements Provider<Reader> {
    private final String resource;

    @Override
    public Reader get() {
        return new InputStreamReader(getClass().getResourceAsStream(resource));
    }

    public ResourceReaderProvider(String resource) {

        this.resource = resource;
    }

    public static Provider<Reader> newTestResourceReaderProvider(String path) {
        return new ResourceReaderProvider(path);
    }
}
