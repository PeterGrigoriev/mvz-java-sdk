package com.movilizer.util;

import org.testng.annotations.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.testng.Assert.assertEquals;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author philippe.guillamet@gdfsuez.com
 */
public class MovilizerTemplateUtilsTest {
    private MovilizerTemplateUtils utils;

    public MovilizerTemplateUtilsTest() {
        this.utils = new MovilizerTemplateUtils();
    }

    @Test
    public void testDatetimeToString() throws Exception {
        Date date = new GregorianCalendar(2013, 1, 13, 13, 60).getTime();
        String datetimeToString = utils.datetimeToString(date);
        assertEquals(datetimeToString, "13-02-2013T14:00");
    }

    @Test
    public void testDateToString() throws Exception {
        Date date = new GregorianCalendar(2013, 1, 13, 13, 60).getTime();
        String dateToString = utils.dateToString(date);
        assertEquals(dateToString, "13-02-2013");
    }

    @Test
    public void testXmlEscape() throws Exception {
        String str = utils.xmlEscape("1 > 0 & 0 < 1 'some VB comment here");
        assertEquals(str, "1 &gt; 0 &amp; 0 &lt; 1 &apos;some VB comment here");
    }
}
