package com.movilizer.connector.mock;

import com.movilitas.movilizer.v15.MovilizerRequest;
import com.movilitas.movilizer.v15.MovilizerResponse;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IMovilizerRequestSender;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.connector.MovilizerCallResult;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMovilizerRequestSender implements IMovilizerRequestSender {

    private static final ILogger logger = ComponentLogger.getInstance("MockRequestSender");

    private MovilizerRequest lastSentRequest;
    private IProxyInfo lastUsedProxyInfo;
    private MovilizerResponse response;


    public MockMovilizerRequestSender() {
        this(new MovilizerResponse());
    }

    public MockMovilizerRequestSender(MovilizerResponse response) {
        this.response = response;
    }

    @Override
    public MovilizerCallResult sendRequest(MovilizerRequest request, IMovilizerCloudSystem system, IProxyInfo proxyInfo) {
        logger.debug("sendRequest: USING MOCK");

        this.lastSentRequest = request;
        this.lastUsedProxyInfo = proxyInfo;
        return new MovilizerCallResult(response);
    }

    public MovilizerRequest getLastSentRequest() {
        return lastSentRequest;
    }

    public IProxyInfo getLastUsedProxyInfo() {
        return lastUsedProxyInfo;
    }

    public void setResponse(MovilizerResponse response) {
        this.response = response;
    }
}
