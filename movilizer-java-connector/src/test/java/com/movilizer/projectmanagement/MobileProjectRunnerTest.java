package com.movilizer.projectmanagement;

import com.movilizer.assignmentmanagement.MobileAssignmentEvent;
import com.movilizer.assignmentmanagement.MobileAssignmentEventType;
import com.movilizer.assignmentmanagement.mock.MockMobileAssignmentManager;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.MovilizerCloudSystem;
import com.movilizer.connector.mock.MockMovilizerRequestSender;
import com.movilizer.projectmanagement.mock.MockMobileProjectManager;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MovilizerUser;
import com.movilizer.usermanagement.MovilizerUserInvitationMethod;
import com.movilizer.usermanagement.MovilizerUserStatus;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.MovilizerConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("FieldCanBeLocal")
public class MobileProjectRunnerTest {


    private MockMovilizerRequestSender requestSender;
    private MockMovilizerProject project;
    private MockMobileProjectManager projectManager;
    private IMobileProjectRunner runner;
    private IMovilizerCloudSystem moveletCloudSystem;
    private IMovilizerCloudSystem masterdataCloudSystem;
    private MockMobileAssignmentManager assignmentManager;
    private IMovilizerUser user;

    @BeforeMethod
    public void setUp() throws Exception {


        project = new MockMovilizerProject();
        projectManager = new MockMobileProjectManager();
        moveletCloudSystem = new MovilizerCloudSystem(1, "pass1", "www.movilizer.com", 1800);
        masterdataCloudSystem = new MovilizerCloudSystem(2, "pass2", "www.movilizer.com", 2600);

        projectManager.addProjectSettings(project.getName(), project.getVersion(), moveletCloudSystem, masterdataCloudSystem);

        assertNotNull(projectManager.getMobileProjectSettings(project.getName(), project.getVersion()));

        requestSender = new MockMovilizerRequestSender();
        assignmentManager = new MockMobileAssignmentManager();

        user = createUser(1111, "Nikolaj Samuraj", "a@b.com");

        runner = new MobileProjectRunner(projectManager, new SingleMobileProjectLoader(project), assignmentManager, requestSender);
    }

    private static MovilizerUser createUser(int employeeNumber, String name, String email) {
        return new MovilizerUser(employeeNumber, name, MovilizerUserInvitationMethod.EMAIL, email, null, MovilizerUserStatus.NEW, new HashMap<String, String>());
    }

    @Test
    public void testRunPullVoid() throws Exception {
        runner.runPull();
        org.testng.Assert.assertTrue(project.getResponseObserver().wasCalled());
    }


    @Test
    public void testRunRegularPush() throws Exception {
        runner.runPush();
        org.testng.Assert.assertTrue(project.isOnPushCallAvailableCalled());
    }

    @Test
    public void testRunPushWithAssignments() throws Exception {
        MobileAssignmentEvent event = createAssignmentEvent(101, user);

        assignmentManager.addEvent(event);
        runner.runPush();
        org.testng.Assert.assertTrue(project.isOnUsersAssignedCalled());
    }

    private MobileAssignmentEvent createAssignmentEvent(int id, IMovilizerUser movilizerUser) {
        MobileAssignmentEvent event = new MobileAssignmentEvent();
        event.setId(id);
        event.setType(MobileAssignmentEventType.ASSIGNED);
        event.setUser(movilizerUser);
        event.setProjectDescription(project);
        return event;
    }

    private List<MobileAssignmentEvent> createAssignmentEvents(int numberOfEvents) {
        List<MobileAssignmentEvent> events = newArrayList();
        for (int i = 0; i < numberOfEvents; i++) {
            MovilizerUser movilizerUser = createUser(1000 + i, "User " + i, "user_" + i + "@nowhere.com");
            MobileAssignmentEvent event = createAssignmentEvent(i, movilizerUser);
            events.add(event);
        }
        return events;
    }

    @Test
    public void testRunPushWithTooManyAssignments() throws Exception {
        int usersPerRun = getConfig().getPushSettings().getNumberOfNewUsersPerRun();

        List<MobileAssignmentEvent> assignmentEvents = createAssignmentEvents(usersPerRun * 2);
        for (MobileAssignmentEvent event : assignmentEvents) {
            assignmentManager.addEvent(event);
        }
        runner.runPush();
        assertFalse(project.getJoinedUsers().isEmpty());
        assertEquals(project.getJoinedUsers().size(), usersPerRun);
    }

    private IMovilizerConfig getConfig() {
        return MovilizerConfig.getInstance(getClass());
    }
}