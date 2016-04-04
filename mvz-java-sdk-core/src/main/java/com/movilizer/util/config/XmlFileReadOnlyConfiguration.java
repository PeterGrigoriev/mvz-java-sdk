package com.movilizer.util.config;

import org.apache.commons.configuration.HierarchicalConfiguration;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Peter.Grigoriev@movilizer.com.
 */
public abstract class XmlFileReadOnlyConfiguration {
    protected final Class classWithResources;
    protected HierarchicalConfiguration configuration;

    public XmlFileReadOnlyConfiguration(Class classWithResources) {
        this.classWithResources = classWithResources;
        IConfigurationProvider configurationProvider = ConfigurationProvider.getInstance(getConfigurationName(), classWithResources, getAdditionalConfigurationNames());
        configuration = configurationProvider.getConfiguration();
    }

    private final static Set<String> EMPTY_SET = newHashSet();

    protected Set<String> getAdditionalConfigurationNames() {
        return EMPTY_SET;
    }

    protected abstract String getConfigurationName();

    public int getInt(String setting) {
        return configuration.getInt(setting);
    }

    public int getInt(String setting, int defaultValue) {
        return hasKey(setting) ? getInt(setting) : defaultValue;
    }

    @SuppressWarnings("UnusedDeclaration")
    public boolean getBoolean(String setting) {
        return configuration.getBoolean(setting);
    }

    public boolean getBoolean(String setting, boolean defaultValue) {
        return hasKey(setting) ? configuration.getBoolean(setting) : defaultValue;
    }

    public boolean hasKey(String setting) {
        return configuration.containsKey(setting);
    }

    public String getString(String setting) {
        return configuration.getString(setting);
    }

    public String getString(String setting, String defaultValue) {
        return hasKey(setting) ? getString(setting) : defaultValue;
    }

    public void reload() {
        configuration = ConfigurationProvider.reloadInstance(getConfigurationName(), classWithResources, getAdditionalConfigurationNames()).getConfiguration();
    }
}
