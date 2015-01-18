package com.movilizer.util;


import static org.testng.Assert.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class TestHelper {
    public static void assertNotEmpty(String value, String name) {
        assertNotNull(value, name + " is null");
        assertFalse(name.isEmpty(), name + " is empty");
    }
}
