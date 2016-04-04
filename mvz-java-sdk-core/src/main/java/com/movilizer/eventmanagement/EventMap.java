package com.movilizer.eventmanagement;

import com.google.common.base.Predicate;
import com.google.common.primitives.Ints;
import com.movilizer.push.EventAcknowledgementStatus;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class EventMap<T extends IEvent>  {
    private final Map<EventAcknowledgementStatus, List<T>> map;

    public EventMap() {
        map = new HashMap<EventAcknowledgementStatus, List<T>>();
        for (EventAcknowledgementStatus eventAcknowledgementStatus : EventAcknowledgementStatus.values()) {
            map.put(eventAcknowledgementStatus, new ArrayList<T>());
        }
    }


    public List<T> getNewEvents() {
        List<T> newOnes = map.get(EventAcknowledgementStatus.NEW);
        List<T> resendOnes = map.get(EventAcknowledgementStatus.RESEND);

        List<T> result = new ArrayList<T>(newOnes);
        result.addAll(resendOnes);
        return result;
    }

    public List<T> get(EventAcknowledgementStatus status) {
        return map.get(status);
    }

    public int[] getEventIds(EventAcknowledgementStatus status, Predicate<T> predicate) {
        List<Integer> ids = new ArrayList<Integer>();
        List<T> events = map.get(status);

        for (T event : events) {
            if (predicate.apply(event)) {
                ids.add(event.getId());
            }
        }

        return Ints.toArray(ids);

    }

    public void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus status) {
        for (Integer eventId : eventIds) {

            acknowledge(eventId, status);
        }
    }

    public void acknowledge(Integer eventId, EventAcknowledgementStatus newStatus) {
        EventAcknowledgementStatus oldStatus = getStatus(eventId);
        if(null == oldStatus) {
            throw new IllegalArgumentException("Event ID is unknown: " + eventId);
        }
        if(oldStatus == newStatus) {
            return;
        }
        List<T> oldEvents = map.get(oldStatus);
        T event = deleteFrom(oldEvents, eventId);
        map.get(newStatus).add(event);
    }

    private T deleteFrom(List<T> events, int eventId) {
        int index = findIndex(events, eventId);
        if(index == -1) {
            throw new IllegalArgumentException("Event ID is unknown: " + eventId);
        }

        return events.remove(index);
    }

    private int findIndex(List<T> events, int eventId) {
        for (int i = 0; i < events.size(); i++) {
            T event = events.get(i);
            if (event.getId() == eventId) {
                return i;
            }
        }
        return -1;
    }

    @Nullable
    public EventAcknowledgementStatus getStatus(int eventId) {
        for (EventAcknowledgementStatus status : map.keySet()) {
            List<T> events = map.get(status);
            if(findIndex(events, eventId) != -1) {
                return status;
            }
        }
        return null;
    }

    public void add(T event) {
        if(getStatus(event.getId()) != null) {
            throw new IllegalArgumentException("Event with given ID exists already");
        }
        map.get(EventAcknowledgementStatus.NEW).add(event);
    }


    public List<T> findAll(Predicate<T> predicate) {
        List<T> result = new ArrayList<T>();

        for (EventAcknowledgementStatus status : map.keySet()) {
            for (T event : map.get(status)) {
                if(predicate.apply(event)) {
                    result.add(event);
                }
            }
        }
        return result;
    }

    public T findFirst(EventAcknowledgementStatus status, Predicate<T> predicate) {
        List<T> events = map.get(status);

        for (T event : events) {
            if(predicate.apply(event)) {
                return event;
            }
        }
        return null;
    }
}
