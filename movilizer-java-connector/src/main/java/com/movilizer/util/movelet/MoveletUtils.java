package com.movilizer.util.movelet;

import com.movilitas.movilizer.v11.MovilizerMovelet;

import java.util.Date;

import static com.movilizer.util.datetime.DateTimeUtils.asDate;
import static com.movilizer.util.datetime.DateTimeUtils.asXmlGregorianCalendar;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MoveletUtils {
    public static Date getValidTillDate(MovilizerMovelet movelet) {
        return asDate(movelet.getValidTillDate());
    }

    public static void setValidTillDate(MovilizerMovelet movelet, Date validityDate) {
        movelet.setValidTillDate(asXmlGregorianCalendar(validityDate));
    }
}
