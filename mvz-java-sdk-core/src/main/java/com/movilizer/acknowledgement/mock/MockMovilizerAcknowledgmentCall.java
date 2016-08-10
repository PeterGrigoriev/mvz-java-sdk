package com.movilizer.acknowledgement.mock;

import com.movilitas.movilizer.v15.MovilizerResponse;
import com.movilizer.acknowledgement.IMovilizerAcknowledgementCall;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMovilizerAcknowledgmentCall implements IMovilizerAcknowledgementCall {

    private MovilizerResponse lastAcknowledgedResponse;

    @Override
    public void acknowledgeResponse(MovilizerResponse response) {
        lastAcknowledgedResponse = response;
    }

    public boolean hasAcknowledgmentBeenCalled() {
        return lastAcknowledgedResponse != null;
    }
}
