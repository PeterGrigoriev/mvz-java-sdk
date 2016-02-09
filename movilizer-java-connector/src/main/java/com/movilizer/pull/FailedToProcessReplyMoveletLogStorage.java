package com.movilizer.pull;

import com.google.inject.Singleton;
import com.movilitas.movilizer.v14.MovilizerReplyMovelet;
import com.movilizer.jaxb.MovilizerJaxbMarshaller;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class FailedToProcessReplyMoveletLogStorage implements IFailedToProcessReplyMoveletBackUpStorage {
    static final ILogger logger = ComponentLogger.getInstance("FAILED_REPLIES");

    @Override
    public void store(MovilizerReplyMovelet replyMovelet) {
        // TODO: get rid of the ReplyMovelet decorator
        if(replyMovelet instanceof ReplyMovelet) {
            replyMovelet = ((ReplyMovelet)replyMovelet).getMovilizerReplyMovelet();
        }
        logger.error("Following Movelet Failed to integrate");
        logger.trace(MovilizerJaxbMarshaller.getInstance().marshall(replyMovelet));
    }
}
