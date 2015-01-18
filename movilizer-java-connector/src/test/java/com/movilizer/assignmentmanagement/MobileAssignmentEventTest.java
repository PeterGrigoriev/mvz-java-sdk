package com.movilizer.assignmentmanagement;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MobileAssignmentEventTest {

    @Test
    public void testGetProjectDescription() throws Exception {
        Assert.assertNull(new MobileAssignmentEvent().getProjectDescription());
    }
}