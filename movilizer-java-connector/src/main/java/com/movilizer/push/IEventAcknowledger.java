package com.movilizer.push;

import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IEventAcknowledger {
    // todo: specific exception type
    void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) throws Exception;
}
