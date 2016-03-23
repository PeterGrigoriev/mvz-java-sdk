package com.movilizer.masterdata;

import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMasterdataAcknowledgerCall {
    private final IMasterdataXmlSetting setting;
    private final Collection<Integer> eventIds;
    private final AcknowledgementStatus status;

    public MockMasterdataAcknowledgerCall(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) {

        this.setting = setting;
        this.eventIds = eventIds;
        this.status = status;
    }

    public IMasterdataXmlSetting getSetting() {
        return setting;
    }

    public Collection<Integer> getEventIds() {
        return eventIds;
    }

    public AcknowledgementStatus getStatus() {
        return status;
    }
}
