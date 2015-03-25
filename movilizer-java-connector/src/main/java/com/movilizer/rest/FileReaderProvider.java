package com.movilizer.rest;

import com.google.inject.Provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class FileReaderProvider implements Provider<Reader> {

    private final File file;

    public FileReaderProvider(File file) {

        this.file = file;
        if (!file.isFile()) {
            throw new IllegalArgumentException(getIsNotAFileMessage());
        }
    }

    private String getIsNotAFileMessage() {
        return "File [" + file + "] is not a file";
    }

    @Override
    public Reader get() {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(getIsNotAFileMessage());
        }
    }
}
