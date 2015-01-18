package com.movilizer.util.config;

import java.util.Set;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author philippe.guillamet@gdfsuez.com
 */
public interface IMoveletSettings {
    String getNamespace();

    String getEnvironment();

    IMoveletCategory getRootCategory();

    boolean isDebug();

    int getMasterDataSystemId();

    Set<IMovilizerMasterDataSystem> getMasterDataSystems();

    Integer getMasterDataSystemId(String pool);

    int getMoveletVersion();

    String getMoveletKey();
}
