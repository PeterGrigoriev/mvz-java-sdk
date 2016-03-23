package com.movilizer.util.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.testng.annotations.Test;

import java.util.List;

import static com.movilizer.util.collection.CollectionUtils.newProperties;
import static org.apache.commons.configuration.ConfigurationConverter.getConfiguration;
import static org.apache.commons.configuration.ConfigurationUtils.convertToHierarchical;
import static org.testng.Assert.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ConfigurationProviderTest {
    @Test
    public void testGetConfiguration() throws Exception {
        Configuration configuration = ConfigurationProvider.getInstance("configuration", getClass()).getConfiguration();
        assertNotNull(configuration);

        checkMovilizerSystemSettings(configuration);
        checkMovilizerMoveletSettings(configuration);
        checkMovilizerPullSettings(configuration);
        checkJdbcSettings(configuration);
    }


    private static HierarchicalConfiguration newConfiguration(String... values) {
        HierarchicalConfiguration configuration = convertToHierarchical(getConfiguration(newProperties(values)));
        ConfigurationNode rootNode = configuration.getRootNode();
        rootNode.setName("configuration");
        return configuration;

    }

    @Test
    public void testMergeIntoAddsValues() {
        HierarchicalConfiguration defaultConfiguration = newConfiguration("oldKey1", "oldValue1", "oldKey2", "oldValue2");
        HierarchicalConfiguration overridingConfiguration  = newConfiguration("newKey", "newValue");

        HierarchicalConfiguration configuration = ConfigurationProvider.mergeAdditionalConfiguration(defaultConfiguration, overridingConfiguration);

        assertEquals(configuration.getString("newKey"), "newValue");
        assertEquals(configuration.getString("oldKey1"), "oldValue1");
        assertEquals(configuration.getString("oldKey2"), "oldValue2");
    }

    @Test
    public void testMergeIntoOverridesValues() {
        HierarchicalConfiguration defaultConfiguration = newConfiguration("oldKey1", "oldValue1", "oldKey2", "oldValue2");
        HierarchicalConfiguration overridingConfiguration  = newConfiguration("newKey", "newValue", "oldKey1", "newValue1");

        HierarchicalConfiguration configuration = ConfigurationProvider.mergeAdditionalConfiguration(defaultConfiguration, overridingConfiguration);

        assertEquals(configuration.getString("newKey"), "newValue");
        assertEquals(configuration.getString("oldKey1"), "newValue1");
        assertEquals(configuration.getString("oldKey2"), "oldValue2");
    }


    private static void checkMovilizerPullSettings(Configuration configuration) {
        checkGreaterThanZero(configuration, "movilizer.pull.number-of-replies");
        List ignoredMoveletKeys = configuration.getList("movilizer.pull.ignore-movelets.key");
        assertNotNull(ignoredMoveletKeys);
        for (Object ignoredMoveletKey : ignoredMoveletKeys) {
            assertNotNull(ignoredMoveletKey);
            assertFalse(((String) ignoredMoveletKey).isEmpty());
        }
    }

    private static void checkMovilizerSystemSettings(Configuration configuration) {
        checkNotNullOrEmpty(configuration, "movilizer.system.endpoint");
        checkGreaterThanZero(configuration, "movilizer.system.id");
        checkNotNullOrEmpty(configuration, "movilizer.system.password");
    }

    private static void checkJdbcSettings(Configuration configuration) {
        checkNotNullOrEmpty(configuration, "db-connections.jdbc.url");
        checkNotNullOrEmpty(configuration, "db-connections.jdbc.user");
        checkNotNullOrEmpty(configuration, "db-connections.jdbc.password");
    }

    private static void checkMovilizerMoveletSettings(Configuration configuration) {
        checkNotNullOrEmpty(configuration, "movilizer.movelet.root-category.name");
        checkGreaterThanZero(configuration, "movilizer.movelet.root-category.icon");
        checkNotNullOrEmpty(configuration, "movilizer.movelet.namespace");
    }

    private static void checkNotNullOrEmpty(Configuration configuration, String setting) {
        String value = configuration.getString(setting);
        assertNotNull(value, "Setting is null: " + setting);
        assertFalse(value.isEmpty(), "Setting is empty: " + setting);
    }

    private static void checkGreaterThanZero(Configuration configuration, String setting) {
        int anInt = configuration.getInt(setting);
        assertTrue(anInt > 0, "Setting is zero: " + setting);
    }
}
