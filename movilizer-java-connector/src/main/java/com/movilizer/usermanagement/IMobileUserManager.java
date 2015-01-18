package com.movilizer.usermanagement;

import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.push.IEventAcknowledger;

import java.util.Collection;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMobileUserManager extends IEventAcknowledger {
    List<IMobileUserEvent> getMobileUserEvents() throws MobileUserException;

    int addUser(IMovilizerUser user) throws MobileUserException;

    List<IMovilizerUser> getMobileUsers() throws MobileUserException;


    int[] getEventIds(Collection<String> deviceAddresses, EventAcknowledgementStatus type);
}