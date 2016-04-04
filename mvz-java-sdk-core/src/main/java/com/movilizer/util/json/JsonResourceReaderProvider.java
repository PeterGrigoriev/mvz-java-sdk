package com.movilizer.util.json;

import com.google.inject.Provider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class JsonResourceReaderProvider implements Provider<Reader> {

    private final Class classWithResources;
    private final String jsonResourceName;

    public JsonResourceReaderProvider(Class classWithResources, String jsonResourceName) {
        this.classWithResources = classWithResources;
        this.jsonResourceName = jsonResourceName;
    }

    @Override
    public Reader get() {
        InputStream stream = classWithResources.getResourceAsStream(jsonResourceName);
        return new InputStreamReader(stream);
    }
}