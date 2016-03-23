package com.movilizer.projectmanagement;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class SingleMobileProjectLoaderTest {

    private MockMovilizerProject project;
    private SingleMobileProjectLoader loader;
    private MobileProjectSettings settings;

    @BeforeMethod
    public void setUp() throws Exception {
        project = new MockMovilizerProject();
        loader = new SingleMobileProjectLoader(project);
        settings = new MobileProjectSettings();
        settings.setName(project.getName());
        settings.setVersion(project.getVersion());
    }

    @Test
    public void testLoadKnownImplementation() throws Exception {
        IMovilizerProject loaded = loader.loadImplementation(settings);
        assertNotNull(loaded);
    }

    @Test
    public void testLoadUnknownImplementation() throws Exception {
        MobileProjectSettings badSettings = new MobileProjectSettings();
        badSettings.setName("OtherProject");
        badSettings.setVersion(33);
        IMovilizerProject loaded = loader.loadImplementation(badSettings);
        assertNull(loaded);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testThrowsLoadNullImplementation() throws Exception {
        loader.loadImplementation(new MobileProjectSettings());
    }

    @Test
    public void testGetAvailableProjectDescriptions() throws Exception {
        Set<IMobileProjectDescription> descriptions = loader.getAvailableProjectDescriptions();
        Assert.assertEquals(descriptions.size(), 1);
    }
}