package com.movilizer.pull;


import com.google.common.primitives.Ints;
import com.movilitas.movilizer.v15.*;
import com.movilizer.assignmentmanagement.IMobileAssignmentManager;
import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.projectmanagement.IMobileProjectManager;
import com.movilizer.projectmanagement.IMovilizerProject;
import com.movilizer.projectmanagement.MobileProjectEventType;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.EventType;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.ArrayList;
import java.util.List;

import static com.movilizer.push.EventAcknowledgementStatus.ACKNOWLEDGED;
import static com.movilizer.push.EventAcknowledgementStatus.SENT;
import static com.movilizer.push.EventAcknowledgementStatus.SYNCED;
import static java.util.Arrays.asList;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ConfigurationMoveletLifecycleReplyObserver implements IMovilizerResponseObserver {
    private final IMobileProjectManager projectManager;
    private final IMobileAssignmentManager assignmentManager;
    private final IMovilizerProject project;
    private final IMoveletKeyWithExtension keyWithExtension;

    public ConfigurationMoveletLifecycleReplyObserver(IMobileProjectManager projectManager, IMobileAssignmentManager assignmentManager, IMovilizerProject project, IMoveletKeyWithExtension keyWithExtension) {
        this.projectManager = projectManager;
        this.assignmentManager = assignmentManager;
        this.project = project;
        this.keyWithExtension = keyWithExtension;
    }

    private final static ILogger logger = ComponentLogger.getInstance("MoveletAcknowledgedReplyObserver");

    @Override
    public void onResponseAvailable(MovilizerResponse response) throws KeepItOnTheCloudException {
        processMoveletAcknowledgements(response);
        processParticipantAcknowledgements(response);
        processParticipantUnassignments(response);
        processMoveletErrors(response);
    }



    private int[] getSentOrAcknowledgedEvents(String deviceAddress) {
        int[] eventIds = assignmentManager.getAssignmentEventIds(project, asList(deviceAddress), EventType.CREATE, ACKNOWLEDGED);

        if (null == eventIds || eventIds.length == 0) {
            eventIds = assignmentManager.getAssignmentEventIds(project, asList(deviceAddress), EventType.CREATE, SYNCED);
        }
        return eventIds;
    }

    private void processParticipantUnassignments(MovilizerResponse response) {
        List<MovilizerMoveletAssignmentDeleted> assignmentsDeleted = response.getMoveletAssignmentDeleted();
        for (MovilizerMoveletAssignmentDeleted moveletAssignmentDeleted : assignmentsDeleted) {
            if (!matches(moveletAssignmentDeleted.getMoveletKey(), moveletAssignmentDeleted.getMoveletKeyExtension(), keyWithExtension)) {
                continue;
            }

            int[] eventIds = assignmentManager.getAssignmentEventIds(project, asList(moveletAssignmentDeleted.getDeviceAddress()), EventType.DELETE, SENT);

            if(null != eventIds && eventIds.length != 0) {
                try {
                    assignmentManager.acknowledge(Ints.asList(eventIds), ACKNOWLEDGED);
                } catch (Exception e) {
                    logger.error("Error while trying to acknowledge assignment event", e);
                }
            }
        }

    }

    private void processParticipantAcknowledgements(MovilizerResponse response) {
        for (MovilizerParticipantAck participantAck : response.getParticipantAck()) {
            if (!matches(participantAck.getMoveletKey(), participantAck.getMoveletKeyExtension(), keyWithExtension)) {
                continue;
            }

            int[] eventIds = assignmentManager.getAssignmentEventIds(project, asList(participantAck.getDeviceAddress()), EventType.CREATE, SENT);

            if(null != eventIds && eventIds.length != 0) {
                try {
                    assignmentManager.acknowledge(Ints.asList(eventIds), ACKNOWLEDGED);
                } catch (Exception e) {
                    logger.error("Error while trying to acknowledge project event", e);
                }
            }
        }
    }

    private void processMoveletAcknowledgements(MovilizerResponse response) {
        for (MovilizerMoveletAck movilizerMoveletAck : response.getMoveletAck()) {
            if (!matches(movilizerMoveletAck.getMoveletKey(), movilizerMoveletAck.getMoveletKeyExtension(), keyWithExtension)) {
                continue;
            }

            Integer eventId = getProjectEventId();
            if(null != eventId) {
                try {
                    projectManager.acknowledge(asList(eventId), ACKNOWLEDGED);
                    return;
                } catch (Exception e) {
                    logger.error("Error while trying to acknowledge project event", e);
                }
            }
        }
    }

    private void processMoveletErrors(MovilizerResponse response) {
        for (MovilizerMoveletError movilizerMoveletError : response.getMoveletError()) {
            logger.error("Cloud reported an error with the movelet [" + movilizerMoveletError.getMoveletKey() + "|" + movilizerMoveletError.getMoveletKeyExtension() + "]: " + movilizerMoveletError.getMessage());
            if (!matches(movilizerMoveletError.getMoveletKey(), movilizerMoveletError.getMoveletKeyExtension(), keyWithExtension)) {
                continue;
            }

            Integer eventId = getProjectEventId();
            if(null != eventId) {
                try {
                    projectManager.acknowledge(asList(eventId), EventAcknowledgementStatus.ERROR);
                    return;
                } catch (Exception e) {
                    logger.error("Error while trying to acknowledge movelet error", e);
                }
            }
        }
    }

    private Integer getProjectEventId() {
        Integer projectEventId = projectManager.getProjectEventId(project, MobileProjectEventType.INIT, SENT);
        if(projectEventId != null) {
            return projectEventId;
        }
        return projectManager.getProjectEventId(project, MobileProjectEventType.UPDATE, SENT);
    }

    private boolean matches(String moveletKey, String moveletKeyExtension, IMoveletKeyWithExtension moveletKeyWithExtension) {

        return moveletKey.equals(moveletKeyWithExtension.getMoveletKey()) && moveletKeyExtension.equals(moveletKeyWithExtension.getMoveletExtension());
    }
}
