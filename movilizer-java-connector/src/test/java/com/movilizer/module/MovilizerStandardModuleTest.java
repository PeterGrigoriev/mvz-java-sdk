package com.movilizer.module;

import com.google.inject.Guice;
import com.movilizer.push.IMovilizerPushCall;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MovilizerStandardModuleTest {

    @Test
    public void testSetUp() throws Exception {
        IMovilizerPushCall pushCall = Guice.createInjector(new MovilizerStandardModule()).getInstance(IMovilizerPushCall.class);
        Assert.assertNotNull(pushCall);
    }

}