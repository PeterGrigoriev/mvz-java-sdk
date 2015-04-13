package com.movilizer.usermanagement.json;

import com.movilizer.projectmanagement.IMobileProjectSettings;
import junit.framework.TestCase;

import static com.movilizer.util.resource.ResourceReaderProvider.newResourceReaderProvider;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileProjectManagerTest extends TestCase {

    private JsonMobileProjectManager projectManager;

    @Override
    public void setUp() throws Exception {
        projectManager = new JsonMobileProjectManager(newResourceReaderProvider("/mobile-projects.json"), null);
    }

    public void testGetMobileProjectSettings() throws Exception {
        IMobileProjectSettings settings = projectManager.getMobileProjectSettings("someMobileApp", 22);
        assertEquals(settings.getId(), 1);
        assertEquals(settings.getName(), "someMobileApp");
    }
}