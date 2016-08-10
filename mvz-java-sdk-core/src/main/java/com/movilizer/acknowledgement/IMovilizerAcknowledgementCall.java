package com.movilizer.acknowledgement;

import com.google.inject.ImplementedBy;
import com.movilitas.movilizer.v15.MovilizerResponse;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerAcknowledgmentCall.class)
public interface IMovilizerAcknowledgementCall {
    void acknowledgeResponse(MovilizerResponse response);
}
