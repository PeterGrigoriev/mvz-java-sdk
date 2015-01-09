package com.movilizer.assignmentmanagement;

import com.movilizer.eventmanagement.IEvent;
import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.usermanagement.IMovilizerUser;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMobileAssignmentEvent extends IEvent {

    MobileAssignmentEventType getType();

    IMovilizerUser getUser();

    IMobileProjectDescription getProjectDescription();

}
