package com.movilizer.push;

import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class EventAcknowledgingPushCallListener implements IEventAcknowledgingPushCallListener {
    private final List<Integer> eventIds;
    private final IEventAcknowledger eventAcknowledger;

    private final ILogger logger = ComponentLogger.getInstance("EventAcknowledgingPushCallListener");

    public EventAcknowledgingPushCallListener(IEventAcknowledger eventAcknowledger) {
        this.eventAcknowledger = eventAcknowledger;
        eventIds = new ArrayList<Integer>();
    }

    @Override
    public void addId(int id) {
        eventIds.add(id);
    }


    @Override
    public void onSuccess() {
        if(eventIds.isEmpty()) {
            return;
        }

        try {
            eventAcknowledger.acknowledge(eventIds, EventAcknowledgementStatus.SENT);
            logger.debug("Successfully acknowledged " + eventIds.size() + " events with status SENT");
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void onFailure() {
        try {
            eventAcknowledger.acknowledge(eventIds, EventAcknowledgementStatus.RESEND);
            logger.warn("Asking to resend " + eventIds.size() + " events. (Service unavailable?)");
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
