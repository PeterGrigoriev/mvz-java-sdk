package com.movilizer.moveletbuilder;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class EmptyMoveletBuilderTest {

    private EmptyMoveletBuilder moveletBuilder;

    @BeforeMethod
    public void setUp() throws Exception {
        moveletBuilder = new EmptyMoveletBuilder();

    }

    @Test
    public void testBuildMovelets() throws Exception {
        assertTrue(moveletBuilder.buildMovelets().isEmpty());
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(EmptyMoveletBuilder.getInstance());
    }

    @Test
    public void testOnRequestAction() throws Exception {
        moveletBuilder.onRequestAction(RequestEvent.Sent);

    }
}