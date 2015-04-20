package com.movilizer.assignmentmanagement;

import com.movilizer.projectmanagement.IMovilizerProject;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.EventType;
import com.movilizer.push.IEventAcknowledger;

import java.util.Collection;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMobileAssignmentManager extends IEventAcknowledger {

    List<IMobileAssignmentEvent> getAssignmentEvents(IMovilizerProject project) throws MobileAssignmentException;
    int[] getAssignmentEventIds(IMovilizerProject project, Collection<String> deviceAddresses, EventType eventType, EventAcknowledgementStatus acknowledgementStatus);
}
