package com.movilizer.cli;

import com.movilizer.assignmentmanagement.IMobileAssignmentEvent;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.EventType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CliMobileAssignmentManagerTest extends CliManagerTest  {

    private CliMobileAssignmentManager assignmentManager;

    @BeforeMethod
    public void setUp() throws Exception {

        assignmentManager = new CliMobileAssignmentManager(COMMAND_LINE_ASSIGN, CliManagerTest.PROJECT_DESCRIPTION);
    }

    @Test
    public void testGetAssignmentEvents() throws Exception {
        List<IMobileAssignmentEvent> assignmentEvents = assignmentManager.getAssignmentEvents(PROJECT);
        assertEquals(assignmentEvents.size(), 1);
    }

    @Test
    public void testGetAssignmentEventsWithInitOption() throws Exception {
        CliMobileAssignmentManager manager = new CliMobileAssignmentManager(COMMAND_LINE_INIT, PROJECT_DESCRIPTION);
        List<IMobileAssignmentEvent> assignmentEvents = manager.getAssignmentEvents(PROJECT);
        assertTrue(assignmentEvents.isEmpty());
    }

    @Test
    public void testGetAssignmentEventIds() throws Exception {
        int[] assignmentEventIds = assignmentManager.getAssignmentEventIds(PROJECT, new ArrayList<String>(), EventType.CREATE, EventAcknowledgementStatus.ACKNOWLEDGED);
        assertEquals(assignmentEventIds.length, 0);
    }

    @Test
    public void testAcknowledge() throws Exception {
        assignmentManager.acknowledge(newArrayList(1, 2), EventAcknowledgementStatus.NEW);
    }
}