package com.movilizer.util.datetime;

import org.testng.annotations.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

import static com.movilizer.util.datetime.DateTimeUtils.*;
import static org.testng.Assert.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class DateTimeUtilsTest {
    @Test
    public void testAsDate() throws Exception {
        assertNull(asDate(null));
        Date date = new Date();
        XMLGregorianCalendar calendar = asXmlGregorianCalendar(date);
        assertEquals(asDate(calendar), date);
    }

    @Test
    public void testAsXmlGregorianCalendar() throws Exception {
        assertNull(asXmlGregorianCalendar(null));
        Date date = new Date();
        XMLGregorianCalendar calendar = asXmlGregorianCalendar(date);
        assertNotNull(calendar);
        assertEquals(calendar.toGregorianCalendar().getTimeInMillis(), date.getTime());
    }

    @Test
    public void testToMovilizerFormat() throws Exception {

        assertEquals(toMovilizerFormat(null), "");

        //noinspection deprecation
        Date date = getDate(2013, 10, 28, 13, 55, 56);
        assertEquals(toMovilizerFormat(date), "2013-10-28T13:55:56");
    }
}
