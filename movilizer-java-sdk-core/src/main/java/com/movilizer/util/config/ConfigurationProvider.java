package com.movilizer.util.config;

import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.OverrideCombiner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.text.MessageFormat.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 *
 * TODO: refactor
 */
class ConfigurationProvider implements IConfigurationProvider {
    public static final HashSet<String> NO_ADDITIONAL_NAMES = newHashSet();
    private static ILogger logger = ComponentLogger.getInstance("MovilizerUtils.ConfigurationProvider");
    private static final Map<String, IConfigurationProvider> INSTANCE_CACHE = new HashMap<String, IConfigurationProvider>();
    private HierarchicalConfiguration configuration;
    private final String configurationName;
    private final Class classWithResources;
    private final Set<String> additionalConfigurationNames;
    private File configFile;


    private ConfigurationProvider(String configurationName, Class classWithResources, Set<String> additionalConfigurationNames) {
        this.configurationName = configurationName;
        this.classWithResources = classWithResources;
        this.additionalConfigurationNames = additionalConfigurationNames;
        reloadConfiguration();
    }

    private ConfigurationProvider(String configurationName, HierarchicalConfiguration configuration) {
        this.configurationName = configurationName;
        classWithResources = ConfigurationProvider.class;
        reloadMainConfiguration(new FileConfiguration(configuration, null));
        additionalConfigurationNames = NO_ADDITIONAL_NAMES;
    }

    public void reloadConfiguration() {
        reloadMainConfiguration(loadConfiguration(configurationName));
        reloadAdditionalConfigurations();
    }

    private void reloadAdditionalConfigurations() {
        if (additionalConfigurationNames != null && !additionalConfigurationNames.isEmpty()) {
            for (String additionalConfigurationName : additionalConfigurationNames) {
                if(hasAdditionalConfiguration(additionalConfigurationName)) {
                    configuration = mergeAdditionalConfiguration(configuration, additionalConfigurationName);
                }
            }
        }
    }

    private void reloadMainConfiguration(FileConfiguration fileConfiguration) {
        configuration = fileConfiguration.getConfiguration();
        configFile = fileConfiguration.getFile();
    }


    public static IConfigurationProvider getInstance(String configurationName, Class classWithResources) {
        return getInstance(configurationName, classWithResources, NO_ADDITIONAL_NAMES);
    }

    public static IConfigurationProvider getInstance(String configurationName, Class classWithResources, Set<String> additionalConfigurationNames) {
        IConfigurationProvider instance = INSTANCE_CACHE.get(configurationName);
        if (null == instance) {
            instance = reloadInstance(configurationName, classWithResources, additionalConfigurationNames);
        }

        return instance;
    }


    public static IConfigurationProvider getInstance(String name, HierarchicalConfiguration configuration) {
        IConfigurationProvider instance = INSTANCE_CACHE.get(name);

        if (null == instance) {
            instance = new ConfigurationProvider(name, configuration);
            INSTANCE_CACHE.put(name, instance);
        }

        return instance;
    }

    public static IConfigurationProvider reloadInstance(String configurationName, Class classWithResources, Set<String> additionalConfigurationNames) {
        IConfigurationProvider instance = new ConfigurationProvider(configurationName, classWithResources, additionalConfigurationNames);
        INSTANCE_CACHE.put(configurationName, instance);
        return instance;
    }


    @Override
    public boolean hasAdditionalConfiguration(String name) {
        try {
            File file = getConfigFile(name);
            return file != null && file.exists();
        } catch (ConfigurationException e) {
            return false;
        }
    }


    private FileConfiguration loadConfiguration(String name) {
        try {
            File configFile = getConfigFile(name);

            if (!configFile.exists()) {
                logger.fatal("Cannot find configuration file: " + configFile.getAbsolutePath());
            } else {
                logger.debug("Found configuration file: " + configFile.getAbsolutePath());
                return new FileConfiguration(new XMLConfiguration(configFile), configFile);
            }
        } catch (ConfigurationException e) {
            logger.fatal(e);
        }
        return new FileConfiguration(new XMLConfiguration(), null);
    }

    private File getConfigFile(String name) throws ConfigurationException {

        try {
            URL codeSourceLocation = classWithResources.getProtectionDomain().getCodeSource().getLocation();
            File base = new File(codeSourceLocation.toURI()).getParentFile();
            return getConfigFile(base, name);
        } catch (URISyntaxException e) {
            logger.error(e);
            throw new ConfigurationException(e);
        }
    }

    private static File getConfigFile(File base, String name) {
        File machineSpecificConfigFile = new File(base, format("{0}-{1}.xml", name, getMachineName()));
        if (machineSpecificConfigFile.exists()) {
            return machineSpecificConfigFile;
        }
        return new File(base, format("{0}.xml", name));
    }

    public static String getMachineName() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            return address.getHostName();
        } catch (UnknownHostException e) {
            logger.error(e);
            return "UNKNOWN";
        }
    }

    public HierarchicalConfiguration mergeAdditionalConfiguration(HierarchicalConfiguration configuration, String additionalConfigurationName) {
        FileConfiguration otherConfiguration = loadConfiguration(additionalConfigurationName);
        if(!otherConfiguration.exists()) {
            logger.warn("Was not able to load additional configuration: [" + additionalConfigurationName + "]");
            return configuration;
        }
        return mergeAdditionalConfiguration(configuration, otherConfiguration.getConfiguration());

    }

    public static HierarchicalConfiguration mergeAdditionalConfiguration(HierarchicalConfiguration defaultConfig, HierarchicalConfiguration configWithOverrides) {
        HierarchicalConfiguration resultConfig = new HierarchicalConfiguration();
        resultConfig.setRootNode(new OverrideCombiner().combine(configWithOverrides.getRootNode(), defaultConfig.getRootNode()));
        return resultConfig;
    }


    @Override
    public HierarchicalConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void save(HierarchicalConfiguration configuration) throws IllegalStateException, IOException, ConfigurationException {
        if (null == configFile) {
            throw new IllegalStateException("ConfigurationProvider.save called before ConfigurationProvider.load");
        }
        new XMLConfiguration(configuration).save(new FileWriter(configFile));
    }

}
