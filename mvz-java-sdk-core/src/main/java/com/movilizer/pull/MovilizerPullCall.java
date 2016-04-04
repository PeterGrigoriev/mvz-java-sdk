package com.movilizer.pull;

import com.google.inject.Inject;
import com.movilizer.connector.*;
import com.movilizer.util.config.MovilizerConfig;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.annotation.Nullable;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerPullCall extends MovilizerCall implements IMovilizerPullCall {
    private static ILogger logger = ComponentLogger.getInstance("MovilizerPullCall");

    @Inject
    public MovilizerPullCall(IMovilizerCloudSystem system, @Nullable IProxyInfo proxyInfo, IMovilizerRequestSender movilizerRequestSender) {
        super(system, proxyInfo, movilizerRequestSender);
    }

    @Override
    protected int getNumberOfRepliesToReceive() {
        return MovilizerConfig.getInstance().getPullSettings().getNumberOfReplies();
    }


    public MovilizerCallResult collectReplies() {
        MovilizerCallResult result = doCallMovilizerCloud();
        resetRequest();

        if (!result.isSuccess()) {
            logger.error(result.getError());
        }
        return result;

    }
}
