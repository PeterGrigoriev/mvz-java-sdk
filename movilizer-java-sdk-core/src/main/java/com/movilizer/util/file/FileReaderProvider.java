package com.movilizer.util.file;

import com.google.inject.Provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class FileReaderProvider implements Provider<Reader>  {

    private final File file;

    public FileReaderProvider(File file) {
        this.file = file;
    }

    @Override
    public Reader get() {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found [" + file + "]", e);
        }
    }

    public static Provider<Reader> newFileReaderProvider(File file) {
        return new FileReaderProvider(file);
    }
}
