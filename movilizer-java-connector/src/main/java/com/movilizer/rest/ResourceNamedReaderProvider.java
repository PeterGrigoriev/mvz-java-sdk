package com.movilizer.rest;

import com.google.inject.Provider;
import com.movilizer.util.resource.ResourceReaderProvider;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ResourceNamedReaderProvider implements INamedReaderProvider {

    private final Class theClass;
    private final String commonPrefix;

    public ResourceNamedReaderProvider(Class theClass, String commonPrefix) {
        this.theClass = theClass;
        this.commonPrefix = commonPrefix;
    }

    @Override
    public Provider<Reader> get(String name) {
        return new ResourceReaderProvider(theClass, commonPrefix + name);
    }
}
