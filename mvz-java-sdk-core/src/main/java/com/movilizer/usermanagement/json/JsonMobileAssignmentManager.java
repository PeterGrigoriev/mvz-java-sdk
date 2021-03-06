package com.movilizer.usermanagement.json;

import com.google.gson.JsonElement;
import com.google.inject.Provider;
import com.movilizer.assignmentmanagement.IMobileAssignmentEvent;
import com.movilizer.assignmentmanagement.IMobileAssignmentManager;
import com.movilizer.assignmentmanagement.MobileAssignmentException;
import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.projectmanagement.IMovilizerProject;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.EventType;

import java.io.Reader;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.movilizer.util.json.JsonUtils.parseJsonArray;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileAssignmentManager implements IMobileAssignmentManager {
    private final Provider<Reader> assignmentEventsReaderProvider;

    public JsonMobileAssignmentManager(Provider<Reader> assignmentEventsReaderProvider) {
        this.assignmentEventsReaderProvider = assignmentEventsReaderProvider;
    }

    @Override
    public List<IMobileAssignmentEvent> getAssignmentEvents(IMobileProjectDescription project) throws MobileAssignmentException {
        List<JsonElement> jsonElements = parseJsonArray(assignmentEventsReaderProvider.get());
        List<IMobileAssignmentEvent> assignmentEvents = newArrayList();
        for (JsonElement jsonElement : jsonElements) {
            assignmentEvents.add(JsonMobileAssignmentEvent.fromJsonObject(jsonElement));
        }

        return assignmentEvents;
    }

    @Override
    public int[] getAssignmentEventIds(IMobileProjectDescription project, Collection<String> deviceAddresses, EventType eventType, EventAcknowledgementStatus acknowledgementStatus) {
        return new int[0];
    }

    @Override
    public void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) throws Exception {
    }
}
