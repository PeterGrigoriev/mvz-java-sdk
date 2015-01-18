package com.movilizer.connector;

import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class MovilizerCloudSystemTest {

    @Test
    public void testEquals() throws Exception {
        assertEquals(new MovilizerCloudSystem(123, "abc", "www.end.com", 12000),
                new MovilizerCloudSystem(123, "abc", "www.end.com", 12000));

        assertFalse(new MovilizerCloudSystem(234, "abc", "www.end.com", 12000).equals(
                new MovilizerCloudSystem(123, "abc", "www.end.com", 12000)));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(new MovilizerCloudSystem(123, "abc", "www.end.com", 12000).hashCode(),
                new MovilizerCloudSystem(123, "abc", "www.end.com", 12000).hashCode());

    }
}