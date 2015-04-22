package com.movilizer.assignmentmanagement.mock;

import com.google.common.base.Predicate;
import com.movilizer.assignmentmanagement.IMobileAssignmentEvent;
import com.movilizer.assignmentmanagement.IMobileAssignmentManager;
import com.movilizer.assignmentmanagement.MobileAssignmentException;
import com.movilizer.eventmanagement.EventMap;
import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.EventType;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMobileAssignmentManager implements IMobileAssignmentManager {
    private final EventMap<IMobileAssignmentEvent> eventMap = new EventMap<IMobileAssignmentEvent>();


    // Project parameter not used for the mock implementation
    @Override
    public List<IMobileAssignmentEvent> getAssignmentEvents(IMobileProjectDescription project) throws MobileAssignmentException {
        return eventMap.getNewEvents();
    }

    @Override
    public int[] getAssignmentEventIds(IMobileProjectDescription project,
                                       Collection<String> deviceAddresses,
                                       final EventType eventType,
                                       EventAcknowledgementStatus acknowledgementStatus) {

        final Set<String> addresses = newHashSet(deviceAddresses);

        return eventMap.getEventIds(acknowledgementStatus, new Predicate<IMobileAssignmentEvent>() {
            public boolean apply(IMobileAssignmentEvent event) {
                return event.getType().matches(eventType) && addresses.contains(event.getUser().getDeviceAddress());
            }
        });
    }

    @Override
    public void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) throws Exception {
        eventMap.acknowledge(eventIds, acknowledgementStatus);
    }

    public void addEvent(IMobileAssignmentEvent event) {
        eventMap.add(event);
    }


    public EventAcknowledgementStatus getStatus(int eventId) {
        return eventMap.getStatus(eventId);
    }

}
