package com.movilizer.pull;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IMovilizerRequestSender;
import com.movilizer.connector.IProxyInfo;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerMasterDataPullCall extends MovilizerPullCall implements IMovilizerMasterDataPullCall {
    @Inject
    public MovilizerMasterDataPullCall(@Named("MasterData") IMovilizerCloudSystem system, IProxyInfo proxyInfo, IMovilizerRequestSender movilizerRequestSender) {
        super(system, proxyInfo, movilizerRequestSender);
    }
}
