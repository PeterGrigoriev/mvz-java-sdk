package com.movilizer.connector;

import com.google.inject.ImplementedBy;
import com.movilitas.movilizer.v12.MovilizerRequest;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerRequestSender.class)
public interface IMovilizerRequestSender {
    MovilizerCallResult sendRequest(MovilizerRequest request, IMovilizerCloudSystem cloudSystem, IProxyInfo proxyInfo);
}
