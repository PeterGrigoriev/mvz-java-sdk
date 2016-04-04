package com.movilizer.usermanagement;

import com.movilizer.eventmanagement.IEvent;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMobileUserEvent extends IEvent {

    MobileUserEventType getType();

    IMovilizerUser getUser();
}