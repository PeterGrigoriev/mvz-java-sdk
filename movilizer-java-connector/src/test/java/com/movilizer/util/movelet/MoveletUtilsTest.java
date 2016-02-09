package com.movilizer.util.movelet;

import com.movilitas.movilizer.v14.MovilizerMovelet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

import static com.movilizer.util.datetime.Dates.afterOneMonth;
import static com.movilizer.util.movelet.MoveletUtils.getValidTillDate;
import static com.movilizer.util.movelet.MoveletUtils.setValidTillDate;
import static org.testng.Assert.assertNotNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MoveletUtilsTest {

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
