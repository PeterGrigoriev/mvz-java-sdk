package com.movilizer.cli;

import com.movilizer.assignmentmanagement.*;
import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.projectmanagement.IMovilizerProject;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.EventType;
import com.movilizer.usermanagement.MovilizerUser;
import org.apache.commons.cli.CommandLine;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CliMobileAssignmentManager implements IMobileAssignmentManager {
    private final CommandLine commandLine;
    private final IMobileProjectDescription projectDescription;

    public CliMobileAssignmentManager(CommandLine commandLine, IMobileProjectDescription projectDescription) {
        this.commandLine = commandLine;
        this.projectDescription = projectDescription;
    }

    @Override
    public List<IMobileAssignmentEvent> getAssignmentEvents(IMobileProjectDescription project) throws MobileAssignmentException {
        if(!commandLine.hasOption(CliOptions.ASSIGN)) {
            return asList();
        }
        String deviceAddress = commandLine.getOptionValue(CliOptions.ASSIGN);

        MobileAssignmentEvent mobileAssignmentEvent = new MobileAssignmentEvent();
        mobileAssignmentEvent.setId(1);
        mobileAssignmentEvent.setProjectDescription(projectDescription);
        mobileAssignmentEvent.setType(MobileAssignmentEventType.ASSIGNED);
        mobileAssignmentEvent.setUser(MovilizerUser.createUserFromDeviceAddress(deviceAddress));

        return asList((IMobileAssignmentEvent)mobileAssignmentEvent);
    }

    @Override
    public int[] getAssignmentEventIds(IMobileProjectDescription project, Collection<String> deviceAddresses, EventType eventType, EventAcknowledgementStatus acknowledgementStatus) {
        return new int[0];
    }

    @Override
    public void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) throws Exception {
        System.out.println("Acknowledged [" + eventIds.size() +  "] events with status [" + acknowledgementStatus + "]");
    }
}
