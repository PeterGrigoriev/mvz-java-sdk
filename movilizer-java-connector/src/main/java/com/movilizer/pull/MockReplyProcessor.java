package com.movilizer.pull;

import com.movilitas.movilizer.v11.MovilizerReplyMovelet;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockReplyProcessor implements IReplyMoveletProcessor {
    private final String acceptedMoveletKeyPrefix;
    private final boolean throwsException;
    private boolean hasBeenCalled;
    private int count = 0;

    public MockReplyProcessor(String acceptedMoveletKeyPrefix, boolean throwsException) {
        this.acceptedMoveletKeyPrefix = acceptedMoveletKeyPrefix;
        this.throwsException = throwsException;
    }

    @Override
    public boolean canProcessReplyMovelet(MovilizerReplyMovelet replyMovelet) {
        return replyMovelet.getMoveletKey().startsWith(acceptedMoveletKeyPrefix);
    }

    @Override
    public void processReplyMovelet(MovilizerReplyMovelet replyMovelet) throws CannotProcessReplyMoveletException {
        hasBeenCalled = true;
        count += 1;
        if (throwsException) {
            throw new CannotProcessReplyMoveletException("Thrown on test purpose");
        }
    }

    public boolean hasBeenCalled() {
        return hasBeenCalled;
    }

    public int getCount() {
        return count;
    }
}
