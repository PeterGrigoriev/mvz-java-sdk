package com.movilizer.util.config;

import org.apache.commons.configuration.HierarchicalConfiguration;

import java.io.File;

/**
 * @author Peter.Grigoriev@movilizer.com.
 */
class FileConfiguration {
    private final HierarchicalConfiguration configuration;
    private final File file;

    public FileConfiguration(HierarchicalConfiguration configuration, File file) {
        this.configuration = configuration;
        this.file = file;
    }

    public HierarchicalConfiguration getConfiguration() {
        return configuration;
    }

    public File getFile() {
        return file;
    }

    public boolean exists() {
        return file != null && file.exists();
    }
}
