package com.movilizer.rest;

import com.google.inject.Provider;

import java.io.Reader;
import java.util.HashMap;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class NamedReaderProvider extends HashMap<String, Provider<Reader>> implements INamedReaderProvider  {
    @Override
    public Provider<Reader> get(String name) {
        return super.get(name);
    }
}
