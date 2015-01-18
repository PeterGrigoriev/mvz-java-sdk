package com.movilizer.pull;

import com.google.inject.ImplementedBy;
import com.movilizer.connector.MovilizerCallResult;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerPullCall.class)
public interface IMovilizerPullCall {
    MovilizerCallResult collectReplies();
}
