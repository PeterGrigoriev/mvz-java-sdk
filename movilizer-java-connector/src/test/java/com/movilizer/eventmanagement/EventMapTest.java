package com.movilizer.eventmanagement;

import com.movilizer.projectmanagement.IMobileProjectEvent;
import com.movilizer.projectmanagement.MobileProjectEvent;
import com.movilizer.projectmanagement.MobileProjectEventType;
import com.movilizer.projectmanagement.MockMovilizerProject;
import com.movilizer.push.EventAcknowledgementStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class EventMapTest {

    private EventMap<IMobileProjectEvent> map;
    private MockMovilizerProject project;

    @BeforeMethod
    public void setUp() throws Exception {
        map = new EventMap<IMobileProjectEvent>();
        project = new MockMovilizerProject();
    }

    @Test
    public void testGetNewEvents() throws Exception {
        map.add(createEvent(1, MobileProjectEventType.SHUTDOWN));
        List<IMobileProjectEvent> newEvents = map.getNewEvents();
        Assert.assertEquals(newEvents.size(), 1);
    }

    private MobileProjectEvent createEvent(int id, MobileProjectEventType eventType) {
        MobileProjectEvent event = new MobileProjectEvent();
        event.setProject(project);
        event.setId(id);
        event.setType(eventType);
        return event;
    }

    @Test
    public void testGet() throws Exception {
        List<IMobileProjectEvent> events = map.get(EventAcknowledgementStatus.ACKNOWLEDGED);
        Assert.assertTrue(events.isEmpty());
        map.add(createEvent(101, MobileProjectEventType.INIT));
        events = map.get(EventAcknowledgementStatus.NEW);
        Assert.assertEquals(events.size(), 1);

    }

    @Test
    public void testGetEventIds() throws Exception {

    }

    @Test
    public void testAcknowledge() throws Exception {

    }


    @Test
    public void testGetStatus() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {

    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testFindFirst() throws Exception {

    }
}