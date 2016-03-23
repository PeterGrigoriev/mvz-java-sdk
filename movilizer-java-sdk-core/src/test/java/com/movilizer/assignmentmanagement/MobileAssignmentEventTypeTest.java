package com.movilizer.assignmentmanagement;

import com.movilizer.push.EventType;
import org.testng.annotations.Test;

import static com.movilizer.assignmentmanagement.MobileAssignmentEventType.ASSIGNED;
import static com.movilizer.assignmentmanagement.MobileAssignmentEventType.UNASSIGNED;
import static org.testng.Assert.*;

public class MobileAssignmentEventTypeTest {

    @Test
    public void testMatches() throws Exception {
        assertTrue(ASSIGNED.matches(EventType.CREATE));
        assertTrue(ASSIGNED.matches(EventType.UPDATE));
        assertTrue(UNASSIGNED.matches(EventType.DELETE));
    }

    @Test
    public void testFromString() throws Exception {
        assertEquals(ASSIGNED, MobileAssignmentEventType.fromString("CREATE"));
        assertEquals(UNASSIGNED, MobileAssignmentEventType.fromString("DELETE"));
    }
}