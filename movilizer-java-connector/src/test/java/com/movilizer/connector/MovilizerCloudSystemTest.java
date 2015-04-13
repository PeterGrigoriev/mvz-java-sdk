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

    @Test
    public void testSetSystemId() {
        MovilizerCloudSystem system = new MovilizerCloudSystem();
        system.setSystemId(123);
        assertEquals(system.getSystemId(), 123);
    }

    @Test
    public void testSetPassword() {
        MovilizerCloudSystem system = new MovilizerCloudSystem();
        system.setPassword("123");
        assertEquals(system.getPassword(), "123");
    }

    @Test
    public void testSetEndpoint() {
        MovilizerCloudSystem system = new MovilizerCloudSystem();
        system.setEndpoint("https://a.b.com");
        assertEquals(system.getEndpoint(), "https://a.b.com");
    }

    @Test
    public void testSetTimeout() {
        MovilizerCloudSystem system = new MovilizerCloudSystem();
        system.setTimeout(18111);
        assertEquals(system.getTimeout(), 18111);
    }
}