package com.movilizer.pull;

import com.google.inject.ImplementedBy;
import com.movilitas.movilizer.v14.MovilizerReplyMovelet;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(FailedToProcessReplyMoveletLogStorage.class)
public interface IFailedToProcessReplyMoveletBackUpStorage {
    void store(MovilizerReplyMovelet replyMovelet);
}
