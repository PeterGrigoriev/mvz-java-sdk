package com.movilizer.usermanagement.json;

import com.google.gson.JsonElement;
import com.google.inject.Provider;
import com.movilizer.projectmanagement.*;
import com.movilizer.push.EventAcknowledgementStatus;

import java.io.Reader;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.movilizer.util.json.JsonUtils.parseJsonArray;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileProjectManager implements IMobileProjectManager {
    private final Provider<Reader> projectDataReaderProvider;
    private final Provider<Reader> projectEventsReaderProvider;

    public JsonMobileProjectManager(Provider<Reader> projectDataReaderProvider) {
        this(projectDataReaderProvider, null);
    }

    public JsonMobileProjectManager(Provider<Reader> projectDataReaderProvider, Provider<Reader> projectEventsReaderProvider) {
        this.projectDataReaderProvider = projectDataReaderProvider;
        this.projectEventsReaderProvider = projectEventsReaderProvider;
    }

    @Override
    public List<IMobileProjectEvent> getMobileProjectEvents(String projectName, int version) throws MobileProjectException {
        return newArrayList();
    }



    @Override
    public IMobileProjectSettings getMobileProjectSettings(final String name, final int version) {
        List<IMobileProjectSettings> settings = getProjectSettings();

        for (IMobileProjectSettings setting : settings) {
            if(setting.getVersion() == version && setting.getName().equals(name)) {
                return setting;
            }
        }

        return null;
    }

    public List<IMobileProjectSettings> getProjectSettings() {
        List<JsonElement> jsonElements = parseJsonArray(projectDataReaderProvider.get());
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
