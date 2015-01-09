package com.movilizer.util.config;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author philippe.guillamet@gdfsuez.com
 */
public class MoveletSettings implements IMoveletSettings {
    private final IMoveletCategory rootCategory;
    private final String namespace;
    private final String environment;
    private boolean debug;
    private final int masterdataSystemId;
    private final int version;
    private final String moveletKey;

    public MoveletSettings(String namespace, String environment, IMoveletCategory rootCategory, boolean debug, int masterdataSystemId, int version, String moveletKey) {
        this.namespace = namespace;
        this.environment = environment;
        this.rootCategory = rootCategory;
        this.debug = debug;
        this.masterdataSystemId = masterdataSystemId;
        this.version = version;
        this.moveletKey = moveletKey;
    }

    public MoveletSettings(String namespace, String environment, IMoveletCategory rootCategory, boolean debug, int masterdataSystemId) {
        this(namespace, environment, rootCategory, debug, masterdataSystemId, 0, "");
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String getEnvironment() {
        return environment;
    }

    public IMoveletCategory getRootCategory() {
        return rootCategory;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public int getMasterdataSystemId() {
        return masterdataSystemId;
    }

    @Override
    public int getMoveletVersion() {
        return version;
    }

    @Override
    public String getMoveletKey() {
        return moveletKey;
    }
}
