package com.movilizer.rest;

import com.google.inject.Provider;

import java.io.Reader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface INamedReaderProvider {
    Provider<Reader> get(String name);
}
