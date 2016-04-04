package com.movilizer.masterdata;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.movilizer.masterdata.MasterdataEventType.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MasterdataEventTypeTest {
    @Test
    public void testFromString() throws Exception {
        Assert.assertEquals(DELETE, fromString("Delete"));
        Assert.assertEquals(DELETE, fromString("delete"));
        Assert.assertEquals(DELETE, fromString("D"));
        Assert.assertEquals(UPDATE, fromString("U"));
        Assert.assertEquals(UPDATE, fromString("C"));
        Assert.assertEquals(UPDATE, fromString(""));
        Assert.assertEquals(UPDATE, fromString(null));
    }
}
