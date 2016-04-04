package com.movilizer.assignmentmanagement;

import com.movilizer.push.EventType;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum MobileAssignmentEventType {
    ASSIGNED, UNASSIGNED;

    public boolean matches(EventType eventType) {
        if(ASSIGNED == this) {
            return eventType == EventType.CREATE || eventType == EventType.UPDATE;
        }
        else {
            return eventType == EventType.DELETE;
        }
    }

    public static MobileAssignmentEventType fromString(String string) {
        String lowerCase = string.toLowerCase();
        if(lowerCase.startsWith("u") || lowerCase.startsWith("d")) {
            return UNASSIGNED;
        }
        return ASSIGNED;
    }
}
