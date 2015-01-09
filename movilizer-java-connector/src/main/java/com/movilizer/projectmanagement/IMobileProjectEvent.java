package com.movilizer.projectmanagement;

import com.movilizer.eventmanagement.IEvent;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMobileProjectEvent extends IEvent {
    IMobileProjectDescription getProjectDescription();

    MobileProjectEventType getType();
}
