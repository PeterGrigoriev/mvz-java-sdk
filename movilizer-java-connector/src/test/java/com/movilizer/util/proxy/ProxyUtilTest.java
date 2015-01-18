package com.movilizer.util.proxy;

import org.testng.annotations.Test;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ProxyUtilTest {
    @Test
    public void testApplyProxy() throws Exception {
        ProxyUtil.applyProxy(null); //should do nothing

    }
}
