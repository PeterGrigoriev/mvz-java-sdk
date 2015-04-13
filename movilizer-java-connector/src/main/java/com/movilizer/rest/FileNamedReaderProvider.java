package com.movilizer.rest;

import com.google.inject.Provider;

import java.io.File;
import java.io.Reader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class FileNamedReaderProvider implements INamedReaderProvider {
    private final File directory;

    public FileNamedReaderProvider(File directory) {
        if(!directory.isDirectory()) {
            throw new IllegalArgumentException("Directory [" + directory + "] is not a directory");
        }
        this.directory = directory;
    }



    @Override
    public Provider<Reader> get(final String name) {
        return new FileReaderProvider(new File(directory, name));
    }
}
