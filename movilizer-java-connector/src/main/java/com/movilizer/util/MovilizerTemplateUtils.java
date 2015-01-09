package com.movilizer.util;

import org.apache.commons.lang.StringEscapeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.text.MessageFormat.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author philippe.guillamet@gdfsuez.com
 */
public class MovilizerTemplateUtils {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final static SimpleDateFormat melDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public String datetimeToString(Date date) {
        if (date == null) {
            return "";
        }
        return format("{0}T{1}", dateFormat.format(date), timeFormat.format(date));
    }

    public String datetimeToMelString(Date date) {
        if (date == null) {
            return "";
        }
        return format("{0}T{1}", melDateFormat.format(date), timeFormat.format(date));
    }

    private final static SimpleDateFormat frenchDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public String datetimeToFrenchString(Date date) {
        if(date == null) {
            return "";
        }
        return format("{0} à {1}", frenchDateFormat.format(date), timeFormat.format(date));
    }

    public String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        return format("{0}", dateFormat.format(date));
    }

    public String xmlEscape(String string) {
        return string == null ? "" : StringEscapeUtils.escapeXml(string);
    }

    public String booleanToString(boolean bool) {
        return bool ? "1" : "0";
    }

    public Object nullToEmpty(Object object) {
        return object == null ? "" : object.toString();
    }

    public String cutStringIfLonger(String myString, int numberOfCharacters)
    {
        if (myString.length() <= numberOfCharacters) {
            return myString;
        }

        return  myString.substring(0,numberOfCharacters) + "...";
    }
}
