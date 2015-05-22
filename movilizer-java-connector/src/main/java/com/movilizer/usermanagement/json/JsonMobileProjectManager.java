package com.movilizer.usermanagement.json;

import com.google.gson.JsonElement;
import com.google.inject.Provider;
import com.movilizer.projectmanagement.*;
import com.movilizer.push.EventAcknowledgementStatus;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.movilizer.usermanagement.json.JsonMobileProjectEvent.createJsonMobileProjectEvent;
import static com.movilizer.util.json.JsonUtils.parseJsonArray;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileProjectManager implements IMobileProjectManager {
    private final Provider<Reader> readerProvider;

    public JsonMobileProjectManager(Provider<Reader> readerProvider) {
        this.readerProvider = readerProvider;
    }

    @Override
    public List<IMobileProjectEvent> getMobileProjectEvents(String projectName, int version) throws MobileProjectException {
        ArrayList<IMobileProjectEvent> mobileProjectEvents = newArrayList();
        List<JsonElement> jsonElements = parseJsonArray(readerProvider.get());
        for (JsonElement jsonElement : jsonElements) {
            mobileProjectEvents.add(createJsonMobileProjectEvent(jsonElement));
        }
        return mobileProjectEvents;
    }

    @Override
    public void acknowledgeProjectEvents(int[] eventIds, EventAcknowledgementStatus acknowledgementStatus) throws MobileProjectException {

    }

    @Override
    public IMobileProjectSettings getMobileProjectSettings(String name, int version) {
        List<IMobileProjectSettings> settings = getProjectSettings();

        for (IMobileProjectSettings setting : settings) {
            if (setting.getVersion() == version && setting.getName().equals(name)) {
                return setting;
            }
        }

        return null;
    }

    public List<IMobileProjectSettings> getProjectSettings() {
        List<JsonElement> jsonElements = parseJsonArray(readerProvider.get());
        List<IMobileProjectSettings> settingsList = newArrayList();
        for (JsonElement jsonElement : jsonElements) {
            settingsList.add(JsonMobileProjectSettings.fromJsonObject(jsonElement));
        }

        return settingsList;
    }

    @Override
    public Integer getProjectEventId(IMobileProjectDescription project, MobileProjectEventType eventType, EventAcknowledgementStatus acknowledgementStatus) {
        return null;
    }

    @Override
    public void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) throws Exception {

    }
}
