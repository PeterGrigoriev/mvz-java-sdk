package com.movilizer.assignmentmanagement;

import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.EventType;
import com.movilizer.push.IEventAcknowledger;

import java.util.Collection;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMobileAssignmentManager extends IEventAcknowledger {

    List<IMobileAssignmentEvent> getAssignmentEvents(IMobileProjectDescription project) throws MobileAssignmentException;
    int[] getAssignmentEventIds(IMobileProjectDescription project, Collection<String> deviceAddresses, EventType eventType, EventAcknowledgementStatus acknowledgementStatus);
}
