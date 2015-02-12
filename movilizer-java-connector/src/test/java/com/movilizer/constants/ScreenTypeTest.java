package com.movilizer.constants;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ScreenTypeTest {

    @Test
    public void testFromIntValue() throws Exception {
        ScreenType[] values = ScreenType.values();
        for (ScreenType screenType : values) {
            int intValue = screenType.getIntValue();
            Assert.assertSame(screenType, ScreenType.fromIntValue(intValue));
        }
    }
}