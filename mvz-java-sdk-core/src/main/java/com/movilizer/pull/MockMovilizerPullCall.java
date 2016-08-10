package com.movilizer.pull;

import com.movilitas.movilizer.v15.MovilizerReplyMovelet;
import com.movilitas.movilizer.v15.MovilizerResponse;
import com.movilizer.connector.MovilizerCallResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMovilizerPullCall implements IMovilizerPullCall {
    private List<MovilizerReplyMovelet> replyMovelets = new ArrayList<MovilizerReplyMovelet>();

    @Override
    public MovilizerCallResult collectReplies() {
        MovilizerResponse movilizerResponse = new MovilizerResponse();
        for (MovilizerReplyMovelet replyMovelet : replyMovelets) {
            movilizerResponse.getReplyMovelet().add(replyMovelet);
        }
        return new MovilizerCallResult(movilizerResponse);
    }

    public void addReplyMovelet(MovilizerReplyMovelet replyMovelet) {
        replyMovelets.add(replyMovelet);
    }
}
