package com.movilizer.util.datetime;

import org.testng.annotations.Test;

import java.util.Date;

import static com.movilizer.util.datetime.Dates.*;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class DatesTest {
    @Test
    public void testNow() throws Exception {
        assertNotNull(now());
    }

    @Test
    public void testAfterOneMonth() throws Exception {
        Date afterOneMonth = afterOneMonth();
        assertNotNull(afterOneMonth);
        long seconds = (afterOneMonth.getTime() - now().getTime())/1000;
        assertTrue(seconds > (long) (27 * 24 * 60 * 60));
    }

    @Test
    public void testAfterNMonths() throws Exception {
        assertNotNull(afterNMonths(3));
        assertTrue(afterNMonths(3).getTime() > afterNMonths(2).getTime());
    }

    @Test
    public void testAfterNYears() throws Exception {
        assertNotNull(afterNYears(100));
        assertTrue(afterNYears(100).getTime() > afterNYears(10).getTime());
    }

    @Test
    public void testNever() throws Exception {
        Date never = forever();
        assertNotNull(never);
        assertTrue(never.getTime() > afterNYears(10).getTime());
    }
}
