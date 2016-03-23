package com.movilizer.usermanagement.json;

import com.google.gson.JsonElement;
import com.google.inject.Provider;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.usermanagement.IMobileUserEvent;
import com.movilizer.usermanagement.IMobileUserManager;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MobileUserException;

import java.io.Reader;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.movilizer.util.json.JsonUtils.parseJsonArray;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileUserManager implements IMobileUserManager {
    private final Provider<Reader> readerProvider;

    public JsonMobileUserManager(Provider<Reader> readerProvider) {
        this.readerProvider = readerProvider;
    }

    @Override
    public List<IMobileUserEvent> getMobileUserEvents() throws MobileUserException {
        return null;
    }

    @Override
    public int addUser(IMovilizerUser user) throws MobileUserException {
        return 0;
    }

    @Override
    public List<IMovilizerUser> getMobileUsers() throws MobileUserException {

        List<JsonElement> jsonUsers = parseJsonArray(readerProvider.get());
        List<IMovilizerUser> users = newArrayList();

        for (JsonElement jsonUser : jsonUsers) {
            users.add(JsonMobileUser.fromJsonObject(jsonUser));
        }
        return users;
    }

    @Override
    public int[] getEventIds(Collection<String> deviceAddresses, EventAcknowledgementStatus type) {
        return new int[0];
    }

    @Override
    public void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) throws Exception {

    }
}
