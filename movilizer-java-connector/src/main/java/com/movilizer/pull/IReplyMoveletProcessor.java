package com.movilizer.pull;

import com.movilitas.movilizer.v14.MovilizerReplyMovelet;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IReplyMoveletProcessor {
    boolean canProcessReplyMovelet(MovilizerReplyMovelet replyMovelet);

    void processReplyMovelet(MovilizerReplyMovelet replyMovelet) throws CannotProcessReplyMoveletException;
}
