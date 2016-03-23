package com.movilizer.html5;

import com.movilizer.constants.ScreenType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CordovaMoveletDataProviderTest {

    private CordovaProject project;
    private CordovaMoveletDataProvider provider;
    private boolean foolScreen = false;

    @BeforeMethod
    public void setUp() throws Exception {
        project = new CordovaProject("testProject", 11) {
            @Override
            public boolean isFullScreen() {
              return foolScreen;
            }
        };
        provider = new CordovaMoveletDataProvider(project);
    }

    @Test
    public void testGetProject() throws Exception {
        assertSame(provider.getProject(), project);
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals(provider.getTitle(), project.getTitle());
    }

    @Test
    public void testGetMoveletKey() throws Exception {
        assertEquals(provider.getDocumentKey(), project.getMoveletKey());
    }

    @Test
    public void testGetVersion() throws Exception {
        assertEquals(provider.getVersion(), project.getVersion());
    }

    @Test
    public void testGetDocumentPool() throws Exception {
        assertEquals(project.getDocumentPool(), provider.getDocumentPool());
    }

    @Test
    public void testGetDocumentKey() throws Exception {
        assertEquals(project.getMoveletKey(), provider.getDocumentKey());
    }

    @Test
    public void testGetScreenType() throws Exception {
        foolScreen = false;
        assertEquals(provider.getScreenType(), ScreenType.HTML5);
        foolScreen = true;
        assertEquals(provider.getScreenType(), ScreenType.HTML5_FULL_SCREEN);
    }
}