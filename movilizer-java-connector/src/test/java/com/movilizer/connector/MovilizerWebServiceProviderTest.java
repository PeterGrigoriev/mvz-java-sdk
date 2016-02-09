package com.movilizer.connector;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerWebServiceProviderTest {
    @Test
    public void testGet() throws Exception {
        MovilizerWebServiceProvider serviceProvider = new MovilizerWebServiceProvider();
        Assert.assertNotNull(serviceProvider.get());
    }
}
