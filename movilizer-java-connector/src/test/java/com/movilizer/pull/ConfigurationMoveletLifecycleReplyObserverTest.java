package com.movilizer.pull;

import com.movilitas.movilizer.v11.MovilizerMoveletSynced;
import com.movilitas.movilizer.v11.MovilizerParticipantAck;
import com.movilitas.movilizer.v11.MovilizerResponse;
import com.movilizer.assignmentmanagement.MobileAssignmentEvent;
import com.movilizer.assignmentmanagement.MobileAssignmentEventType;
import com.movilizer.assignmentmanagement.mock.MockMobileAssignmentManager;
import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.projectmanagement.MockMovilizerProject;
import com.movilizer.projectmanagement.mock.MockMobileProjectManager;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MovilizerUser;
import com.movilizer.usermanagement.MovilizerUserInvitationMethod;
import com.movilizer.usermanagement.MovilizerUserStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

public class ConfigurationMoveletLifecycleReplyObserverTest {


    private MockMobileProjectManager projectManager;
    private MockMovilizerProject project;
    private MockMobileAssignmentManager assignmentManager;
    private ConfigurationMoveletLifecycleReplyObserver replyObserver;
    private IMovilizerUser user;
    private IMoveletKeyWithExtension configurationMoveletKey;

    @BeforeMethod
    public void setUp() throws Exception {
        projectManager = new MockMobileProjectManager();
        assignmentManager = new MockMobileAssignmentManager();
        project = new MockMovilizerProject();

        user = new MovilizerUser(10, "Ivan Pitkoff", MovilizerUserInvitationMethod.EMAIL, "pitkoff@yahoo.ru", null, MovilizerUserStatus.NEW, new HashMap<String, String>());


        configurationMoveletKey = project.getConfigurationMoveletKey();
        replyObserver = new ConfigurationMoveletLifecycleReplyObserver(
                projectManager, assignmentManager, project, configurationMoveletKey
        );
    }

    @Test
    public void testOnResponseAvailableWithMoveletSynced() throws Exception {
        MobileAssignmentEvent event = new MobileAssignmentEvent();
        event.setId(345);
        event.setType(MobileAssignmentEventType.ASSIGNED);
        event.setProjectDescription(project);
        event.setUser(user);



        assignmentManager.addEvent(event);
        assignmentManager.acknowledge(asList(event.getId()), EventAcknowledgementStatus.SENT);
        assignmentManager.acknowledge(asList(event.getId()), EventAcknowledgementStatus.ACKNOWLEDGED);


        MovilizerResponse response = new MovilizerResponse();
        MovilizerMoveletSynced movilizerMoveletSynced = new MovilizerMoveletSynced();
        movilizerMoveletSynced.setDeviceAddress(user.getDeviceAddress());
        movilizerMoveletSynced.setMoveletKey(configurationMoveletKey.getMoveletKey());
        movilizerMoveletSynced.setMoveletKeyExtension(configurationMoveletKey.getMoveletExtension());

        response.getMoveletSynced().add(movilizerMoveletSynced);

        replyObserver.onResponseAvailable(response);

        EventAcknowledgementStatus status = assignmentManager.getStatus(event.getId());
        assertEquals(status, EventAcknowledgementStatus.SYNCED);

    }

    @Test
    public void testOnResponseAvailableWithParticipantAck() throws Exception {
        MobileAssignmentEvent event = getMobileAssignmentEvent();

        assignmentManager.addEvent(event);
        assignmentManager.acknowledge(asList(event.getId()), EventAcknowledgementStatus.SENT);

        MovilizerResponse response = new MovilizerResponse();
        MovilizerParticipantAck participantAck = new MovilizerParticipantAck();
        participantAck.setDeviceAddress(user.getDeviceAddress());
        participantAck.setMoveletKey(configurationMoveletKey.getMoveletKey());
        participantAck.setMoveletKeyExtension(configurationMoveletKey.getMoveletExtension());

        response.getParticipantAck().add(participantAck);

        replyObserver.onResponseAvailable(response);

        EventAcknowledgementStatus status = assignmentManager.getStatus(event.getId());
        assertEquals(status, EventAcknowledgementStatus.ACKNOWLEDGED);
    }

    private MobileAssignmentEvent getMobileAssignmentEvent() {
        MobileAssignmentEvent event = new MobileAssignmentEvent();
        event.setProjectDescription(project);
        event.setType(MobileAssignmentEventType.ASSIGNED);
        event.setUser(user);
        event.setId(1234);
        return event;
    }
}