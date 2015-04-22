package com.movilizer.rest;

import com.google.gson.JsonObject;
import com.google.inject.Provider;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.IEventAcknowledger;
import org.apache.http.client.HttpClient;

import java.util.Collection;

import static com.movilizer.push.EventAcknowledgementStatus.OK;
import static com.movilizer.util.json.JsonUtils.toJsonArray;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class WebServiceEventAcknowledger implements IEventAcknowledger {


    private final RestfulWebService webService;

    public WebServiceEventAcknowledger(String endpoint, Provider<HttpClient> httpClientProvider) {
        this.webService = new RestfulWebService(endpoint, httpClientProvider);
    }

    @Override
    public void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) throws Exception {
        webService.put(toJsonObject(eventIds, acknowledgementStatus));
    }

    private JsonObject toJsonObject(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) {
        JsonObject object = new JsonObject();

        object.addProperty("status",  getBackendName(acknowledgementStatus));
        object.add("eventIds", toJsonArray(eventIds));
        return object;

    }

    protected String getBackendName(EventAcknowledgementStatus acknowledgementStatus) {
        return acknowledgementStatus.name();
    }
}
