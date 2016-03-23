package com.movilizer.usermanagement.json;

import com.movilizer.projectmanagement.IMobileProjectSettings;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.movilizer.util.resource.ResourceReaderProvider.newResourceReaderProvider;
import static org.testng.Assert.assertEquals;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileProjectManagerTest {

    private JsonMobileProjectManager projectManager;

    @BeforeTest
    public void setUp() throws Exception {
        projectManager = new JsonMobileProjectManager(newResourceReaderProvider("/mobile-projects.json"), null);
    }

    @Test
    public void testGetMobileProjectSettings() throws Exception {
        IMobileProjectSettings settings = projectManager.getMobileProjectSettings("someMobileApp", 22);
        assertEquals(settings.getId(), 1);
        assertEquals(settings.getName(), "someMobileApp");
    }
}