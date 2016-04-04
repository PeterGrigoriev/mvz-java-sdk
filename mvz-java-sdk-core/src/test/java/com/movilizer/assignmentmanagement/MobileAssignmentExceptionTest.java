package com.movilizer.assignmentmanagement;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MobileAssignmentExceptionTest {

    @Test
    public void testConstructor() throws Exception {
        IllegalAccessError cause = new IllegalAccessError();

        @SuppressWarnings("ThrowableInstanceNeverThrown")
        MobileAssignmentException exception = new MobileAssignmentException("test", cause);

        assertEquals(exception.getMessage(), "test");
        assertSame(exception.getCause(), cause);


    }
}