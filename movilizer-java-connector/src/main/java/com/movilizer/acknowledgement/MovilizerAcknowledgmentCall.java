package com.movilizer.acknowledgement;

import com.google.inject.Inject;
import com.movilitas.movilizer.v14.MovilizerResponse;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IMovilizerRequestSender;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.connector.MovilizerCall;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.annotation.Nullable;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerAcknowledgmentCall extends MovilizerCall implements IMovilizerAcknowledgementCall {
    private static final ILogger logger = ComponentLogger.getInstance("Acknowledgment");

    @Inject
    public MovilizerAcknowledgmentCall(IMovilizerCloudSystem system, @Nullable IProxyInfo proxyInfo, IMovilizerRequestSender movilizerRequestSender) {
        super(system, proxyInfo, movilizerRequestSender);
    }

    @Override
    protected int getNumberOfRepliesToReceive() {
        return 0;
    }


    @Override
    public void acknowledgeResponse(MovilizerResponse response) {
        String requestAcknowledgeKey = response.getRequestAcknowledgeKey();
        if (null == requestAcknowledgeKey) { // nothing to acknowledge: response is empty
            return;
        }

        accessRequest().setRequestAcknowledgeKey(requestAcknowledgeKey);
        doCallMovilizerCloud();
        logger.trace("response acknowledgement sent for: " + requestAcknowledgeKey);
    }
}
