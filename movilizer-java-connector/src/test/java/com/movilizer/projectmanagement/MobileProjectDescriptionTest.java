package com.movilizer.projectmanagement;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MobileProjectDescriptionTest {

    private MobileProjectDescription description;

    @BeforeMethod
    public void setUp() throws Exception {
        description = new MobileProjectDescription("TestProject", 2);

    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(description.getName(), "TestProject");
    }

    @Test
    public void testGetVersion() throws Exception {
        Assert.assertEquals(description.getVersion(), 2);
    }

    @Test
    public void testEquals() throws Exception {
        Assert.assertEquals(description, new MobileProjectDescription("TestProject", 2));
        Assert.assertFalse(description.equals(new MobileProjectDescription("TestProject", 3)));

    }

    @Test
    public void testHashCode() throws Exception {
        int hashCode = description.hashCode();
        Assert.assertFalse(hashCode > 0);
    }

    @Test
    public void testToString() throws Exception {
        String string = description.toString();
        Assert.assertTrue(string.contains("TestProject"));
        Assert.assertTrue(string.contains("2"));
    }
}