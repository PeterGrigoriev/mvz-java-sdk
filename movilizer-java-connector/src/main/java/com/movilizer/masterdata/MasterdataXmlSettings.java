package com.movilizer.masterdata;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.StringReader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MasterdataXmlSettings implements IMasterdataXmlSetting {
    private final String pool;
    private final String targetPool;

    private final IMasterdataFieldNames fieldNames;
    private final int limit;
    private final String subscriber;
    private final int numberOfLoops;

    public MasterdataXmlSettings(String subscriber, String pool, int limit, int numberOfLoops, IMasterdataFieldNames fieldNames) {
        this(subscriber, pool, limit, numberOfLoops, fieldNames, null);
    }

    public MasterdataXmlSettings(String subscriber, String pool, int limit, int numberOfLoops, IMasterdataFieldNames fieldNames, String targetPool) {
        this.pool = pool;
        this.numberOfLoops = numberOfLoops;
        this.fieldNames = fieldNames;
        this.limit = limit;
        this.subscriber = subscriber;
        this.targetPool = targetPool;
    }

    public static IMasterdataXmlSetting fromXml(String xml) throws ConfigurationException {
        XMLConfiguration xmlConfiguration = new XMLConfiguration();
        xmlConfiguration.load(new StringReader(xml));
        return read(xmlConfiguration);
    }

    public static IMasterdataXmlSetting read(HierarchicalConfiguration configuration) {
        String pool = configuration.getString("pool");
        String subscriber = configuration.getString("subscriber");
        int limit = configuration.getInt("limit");
        int numberOfLoops = 1;
        if (configuration.containsKey("loops")) {
            numberOfLoops = configuration.getInt("loops");
        }
        IMasterdataFieldNames fieldNames = MasterdataFieldNames.readFieldNames(configuration.configurationAt("fields"));
        String targetPool = configuration.getString("target-pool", null);
        return new MasterdataXmlSettings(subscriber, pool, limit, numberOfLoops, fieldNames, targetPool);
    }

    @Override
    public String getPool() {
        return pool;
    }

    @Override
    public String getTargetPool() {
        return targetPool;
    }

    @Override
    public boolean isReference() {
        return targetPool != null;
    }

    @Override
    public IMasterdataFieldNames getFieldNames() {
        return fieldNames;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public String getSubscriber() {
        return subscriber;
    }

    @Override
    public int getNumberOfLoops() {
        return numberOfLoops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MasterdataXmlSettings)) return false;

        MasterdataXmlSettings that = (MasterdataXmlSettings) o;

        if (!pool.equals(that.pool)) return false;
        if (!subscriber.equals(that.subscriber)) return false;
        if (targetPool != null ? !targetPool.equals(that.targetPool) : that.targetPool != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pool.hashCode();
        result = 31 * result + (targetPool != null ? targetPool.hashCode() : 0);
        result = 31 * result + subscriber.hashCode();
        return result;
    }
}
