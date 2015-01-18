package com.movilizer.masterdata;

import org.apache.commons.configuration.HierarchicalConfiguration;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MasterdataFieldNames implements IMasterdataFieldNames {
    private String group;
    private String objectId;
    private String eventId = "EVENT_ID";
    private String eventType = "EVENT_TYPE";
    private String description = null;


    private String filter1;
    private String filter2;
    private String filter3;
    private String filter4;
    private String filter5;
    private String filter6;

    public static IMasterdataFieldNames readFieldNames(HierarchicalConfiguration conf) {

        MasterdataFieldNames fieldNames = new MasterdataFieldNames(
                conf.getString("group"),
                conf.getString("object-id"),
                conf.getString("description"),
                conf.getString("filter1"),
                conf.getString("filter2"),
                conf.getString("filter3"),
                conf.getString("filter4"),
                conf.getString("filter5"),
                conf.getString("filter6")
        );

        String eventId = conf.getString("event-id");
        if (null != eventId) {
            fieldNames.setEventId(eventId);
        }
        String eventType = conf.getString("event-type");
        if (null != eventType) {
            fieldNames.setEventType(eventType);
        }
        return fieldNames;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getEventType() {
        return eventType;
    }


    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

    public void setFilter3(String filter3) {
        this.filter3 = filter3;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }

    @Override
    public String getFilter1() {
        return filter1;
    }

    @Override
    public String getFilter2() {
        return filter2;
    }

    @Override
    public String getFilter3() {
        return filter3;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MasterdataFieldNames(String group, String objectId, String description) {
        this(group, objectId, description, null, null, null);
    }

    public MasterdataFieldNames(String group, String objectId, String description, String filter1, String filter2, String filter3) {
        this(group, objectId, description, filter1, filter2, filter3, null, null, null);
    }

    public MasterdataFieldNames(String group, String objectId, String description, String filter1, String filter2, String filter3, String filter4, String filter5, String filter6) {
        this.group = group;
        this.objectId = objectId;
        this.description = description;
        this.filter1 = filter1;
        this.filter2 = filter2;
        this.filter3 = filter3;
        this.filter4 = filter4;
        this.filter5 = filter5;
        this.filter6 = filter6;
    }

    @Override
    public String getFilter4() {
        return filter4;
    }

    @Override
    public String getFilter5() {
        return filter5;
    }

    @Override
    public String getFilter6() {
        return filter6;
    }

    public void setFilter4(String filter4) {
        this.filter4 = filter4;
    }

    public void setFilter5(String filter5) {
        this.filter5 = filter5;
    }

    public void setFilter6(String filter6) {
        this.filter6 = filter6;
    }
}
