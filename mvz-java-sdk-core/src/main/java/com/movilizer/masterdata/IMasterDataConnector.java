package com.movilizer.masterdata;

import com.google.inject.ImplementedBy;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MasterDataConnector.class)
public interface IMasterDataConnector {
    void run();
    void cleanPool(String pool);
    void cleanAllPools();
}
