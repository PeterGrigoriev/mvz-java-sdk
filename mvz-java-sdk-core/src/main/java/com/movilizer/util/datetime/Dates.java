package com.movilizer.util.datetime;

import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

import static com.movilizer.util.datetime.DateTimeUtils.getDate;
import static org.apache.commons.lang.time.DateUtils.addYears;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class Dates {
    private static final Date IN_ONE_HUNDRED_YEARS = addYears(getDate(2013, 10, 1), 100);

    public static Date now() {
        return new Date();
    }

    public static Date afterOneMonth() {
        return afterNMonths(1);
    }

    public static Date afterTwoMonths() {
        return afterNMonths(2);
    }

    public static Date afterNMonths(int amount) {
        return DateUtils.addMonths(new Date(), amount);
    }

    public static Date afterNYears(int nYears) {
        return addYears(new Date(), nYears);
    }


    public static Date afterOneYear() {
        return afterNYears(1);
    }

    public static Date forever() {
        return IN_ONE_HUNDRED_YEARS;
    }
}
