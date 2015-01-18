package com.movilizer.projectmanagement;

import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.IEventAcknowledger;

import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMobileProjectManager extends IEventAcknowledger {
    List<IMobileProjectEvent> getMobileProjectEvents(String projectName, int version) throws MobileProjectException;

    void acknowledgeProjectEvents(int[] eventIds, EventAcknowledgementStatus acknowledgementStatus) throws MobileProjectException;

    IMobileProjectSettings getMobileProjectSettings(String name, int version);

    Integer getProjectEventId(IMobileProjectDescription project, MobileProjectEventType eventType, EventAcknowledgementStatus acknowledgementStatus);
}
