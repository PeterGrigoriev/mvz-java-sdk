package com.movilizer.util.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;

import java.io.IOException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public abstract class XmlFileConfiguration extends XmlFileReadOnlyConfiguration {

    protected XmlFileConfiguration(Class classWithResources) {
        super(classWithResources);
    }


    protected abstract HierarchicalConfiguration toConfiguration();


    public void save() throws IOException, ConfigurationException {
        configuration = toConfiguration();
        ConfigurationProvider.getInstance(getConfigurationName(), classWithResources).save(configuration);
    }


}
