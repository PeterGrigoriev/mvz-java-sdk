package com.movilizer.util.datetime;

import com.movilitas.movilizer.v11.MovilizerMovelet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

import static com.movilizer.util.datetime.Dates.afterOneMonth;
import static com.movilizer.util.datetime.ValidityDateUtils.getValidTillDate;
import static com.movilizer.util.datetime.ValidityDateUtils.setValidTillDate;
import static org.testng.Assert.assertNotNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ValidityDateUtilsTest {

    @Test
    public void testGetAndSetValidityDate() {
        MovilizerMovelet movelet = new MovilizerMovelet();

        Date date = afterOneMonth();
        setValidTillDate(movelet, date);
        Date restored = getValidTillDate(movelet);
        assertNotNull(restored);
        Assert.assertEquals(restored, date);
    }
}
