package com.movilizer.util.config;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author philippe.guillamet@gdfsuez.com
 */
public interface IMoveletSettings {
    String getNamespace();

    String getEnvironment();

    IMoveletCategory getRootCategory();

    boolean isDebug();

    int getMasterdataSystemId();

    int getMoveletVersion();

    String getMoveletKey();
}
