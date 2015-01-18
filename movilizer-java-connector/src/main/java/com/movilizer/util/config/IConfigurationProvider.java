package com.movilizer.util.config;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;

import java.io.IOException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
interface IConfigurationProvider {
    HierarchicalConfiguration getConfiguration();

    void save(HierarchicalConfiguration configuration) throws IOException, ConfigurationException;

    boolean hasAdditionalConfiguration(String additionalConfigurationName);
}
