package com.movilizer.util.datetime;

import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class DateTimeUtils {
    private static final ILogger logger = ComponentLogger.getInstance("DateTimeUtils");
    private static DatatypeFactory datatypeFactory;

    public static Date asDate(XMLGregorianCalendar calendar) {
        return calendar == null ? null : calendar.toGregorianCalendar().getTime();
    }

    public static XMLGregorianCalendar asXmlGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(date.getTime());
        return getDatatypeFactory().newXMLGregorianCalendar(gregorianCalendar);
    }


    private static DatatypeFactory getDatatypeFactory() {
        return datatypeFactory == null ? (datatypeFactory = loadDatatypeFactory()) : datatypeFactory;
    }

    private static DatatypeFactory loadDatatypeFactory() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
            return datatypeFactory;
        } catch (DatatypeConfigurationException e) {
            logger.fatal(e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * Movilizer date format: 2013-10-26T21:32:52
     */

    private static final SimpleDateFormat MOVILIZER_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static String toMovilizerFormat(Date date) {
        return null == date ? "" : MOVILIZER_DATE_FORMAT.format(date);
    }


    @SuppressWarnings("deprecation")
    public static Date getDate(int year, int month, int day) {
        return new Date(year - 1900, month - 1, day);
    }

    @SuppressWarnings("deprecation")
    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        return new Date(year - 1900, month - 1, day, hour, minute, second);
    }
}
