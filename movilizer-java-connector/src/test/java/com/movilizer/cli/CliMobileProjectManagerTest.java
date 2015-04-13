package com.movilizer.cli;

import com.movilizer.projectmanagement.IMobileProjectEvent;
import com.movilizer.projectmanagement.IMobileProjectSettings;
import com.movilizer.projectmanagement.MobileProjectEventType;
import com.movilizer.push.EventAcknowledgementStatus;
import org.apache.commons.cli.CommandLine;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class CliMobileProjectManagerTest extends CliManagerTest {


    private CliMobileProjectManager projectManager;

    private CliMobileProjectManager getProjectManager(CommandLine commandLine) {
        return new CliMobileProjectManager(config, PROJECT_DESCRIPTION, commandLine);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        projectManager = getProjectManager(COMMAND_LINE_INIT);

    }

    @Test
    public void testGetMobileProjectEvents() throws Exception {

        List<IMobileProjectEvent> mobileProjectEvents = projectManager.getMobileProjectEvents(PROJECT_NAME, VERSION);
        assertEquals(1, mobileProjectEvents.size());
    }

    @Test
    public void testGetMobileProjectEventsWithNoInit() throws Exception {
        CliMobileProjectManager otherManager = getProjectManager(COMMAND_LINE_ASSIGN);
        List<IMobileProjectEvent> mobileProjectEvents = otherManager.getMobileProjectEvents(PROJECT_NAME, VERSION);
        assertEquals(0, mobileProjectEvents.size());
    }

    @Test
    public void testAcknowledgeProjectEvents() throws Exception {
        projectManager.acknowledgeProjectEvents(new int[]{1, 2}, EventAcknowledgementStatus.ACKNOWLEDGED);
    }

    @Test
    public void testGetMobileProjectSettings() throws Exception {
        IMobileProjectSettings settings = projectManager.getMobileProjectSettings(PROJECT_NAME, VERSION);
        assertNotNull(settings.getMoveletCloudSystem().getPassword());
    }

    @Test
    public void testGetProjectEventId() throws Exception {
        assertNull(projectManager.getProjectEventId(PROJECT_DESCRIPTION, MobileProjectEventType.INIT, EventAcknowledgementStatus.NEW));
    }

    @Test
    public void testAcknowledge() throws Exception {
        projectManager.acknowledge(new ArrayList<Integer>(), EventAcknowledgementStatus.OK);
    }
}