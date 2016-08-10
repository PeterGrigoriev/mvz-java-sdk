package com.movilizer.connector;

import com.movilitas.movilizer.v15.MovilizerRequest;

import javax.annotation.Nullable;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
public abstract class MovilizerCall {
    private final IMovilizerCloudSystem system;
    private final IProxyInfo proxyInfo;
    private final IMovilizerRequestSender movilizerRequestSender;
    private MovilizerRequest request;

    public MovilizerCall(IMovilizerCloudSystem system, @Nullable IProxyInfo proxyInfo, IMovilizerRequestSender movilizerRequestSender) {
        this.system = system;
        this.proxyInfo = proxyInfo;
        this.movilizerRequestSender = movilizerRequestSender;
        resetRequest();
    }

    public IMovilizerCloudSystem getSystem() {
        return system;
    }

    protected MovilizerCallResult doCallMovilizerCloud() {
        return movilizerRequestSender.sendRequest(request, system, proxyInfo);
    }


    public MovilizerRequest accessRequest() {
        return request;
    }

    public void resetRequest() {
        resetRequest(new MovilizerRequest());
    }


    protected abstract int getNumberOfRepliesToReceive();

    public void resetRequest(MovilizerRequest request) {
        int numberOfRepliesToReceive = getNumberOfRepliesToReceive();
        request.setNumResponses(numberOfRepliesToReceive);
        if (numberOfRepliesToReceive == 0) {
            request.setResponseSize(0);
            request.setSynchronousResponse(false);
        }
        request.setSystemId(getSystem().getSystemId());
        request.setSystemPassword(getSystem().getPassword());
        request.setUseAutoAcknowledge(false);
        this.request = request;
    }
}
