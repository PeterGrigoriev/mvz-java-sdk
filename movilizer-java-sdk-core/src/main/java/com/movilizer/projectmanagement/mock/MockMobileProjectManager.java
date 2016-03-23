package com.movilizer.projectmanagement.mock;

import com.google.common.base.Predicate;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.eventmanagement.EventMap;
import com.movilizer.projectmanagement.*;
import com.movilizer.push.EventAcknowledgementStatus;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMobileProjectManager implements IMobileProjectManager {

    private final EventMap<IMobileProjectEvent> eventMap = new EventMap<IMobileProjectEvent>();

    private Set <IMobileProjectSettings> projectDescriptions = new HashSet<IMobileProjectSettings>();

    private int lastProjectId = 0;

    public void addProjectSettings(String name, int version, IMovilizerCloudSystem moveletCloudSystem, IMovilizerCloudSystem masterdataCloudSystem) {
        lastProjectId++;

        MobileProjectSettings projectSettings = new MobileProjectSettings();
        projectSettings.setId(lastProjectId);
        projectSettings.setName(name);
        projectSettings.setVersion(version);
        projectSettings.setMoveletCloudSystem(moveletCloudSystem);
        projectSettings.setMasterDataCloudSystem(masterdataCloudSystem);

        projectDescriptions.add(projectSettings);
    }

    public Set<IMobileProjectSettings> getMobileProjectSettings() throws MobileProjectException {
        return projectDescriptions;
    }

    @Override
    public List<IMobileProjectEvent> getMobileProjectEvents(final String projectName, final int version) throws MobileProjectException {
        return eventMap.findAll(new Predicate<IMobileProjectEvent>() {
            public boolean apply(IMobileProjectEvent event) {
                return matches(event, projectName, version);
            }
        });
    }

    private boolean matches(IMobileProjectEvent event, String projectName, int version) {
        return event != null && matches(event.getProjectDescription(), projectName, version);
    }

    private boolean matches(IMobileProjectDescription projectDescription, String projectName, int version) {
        return projectDescription != null && projectDescription.getName() != null &&
               projectDescription.getName().equals(projectName) && projectDescription.getVersion() == version;

    }




    @Override
    public IMobileProjectSettings getMobileProjectSettings(String name, int version) {
        for (IMobileProjectSettings projectDescription : projectDescriptions) {
            if(projectDescription.getName().equals(name) && projectDescription.getVersion() == version) {
                return projectDescription;
            }
        }
        return null;
    }

    @Override
    public Integer getProjectEventId(final IMobileProjectDescription project, final MobileProjectEventType eventType, EventAcknowledgementStatus acknowledgementStatus) {
        IMobileProjectEvent event = eventMap.findFirst(acknowledgementStatus, new Predicate<IMobileProjectEvent>() {
            public boolean apply(IMobileProjectEvent event) {
                return matches(event, project) && event.getType() == eventType;
            }
        });
        return event == null ? null : event.getId();

    }

    private boolean matches(IMobileProjectEvent event, IMobileProjectDescription project) {
        return matches(event, project.getName(), project.getVersion());
    }


    @Override
    public void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) throws Exception {
        eventMap.acknowledge(eventIds, acknowledgementStatus);
    }

    public EventAcknowledgementStatus getStatus(int id) {
        return eventMap.getStatus(id);
    }

    public void addEvent(MobileProjectEvent event) {
        eventMap.add(event);
    }
}
