package com.movilizer.moveletbuilder;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

public class MoveletDataProviderBaseTest {

    @Test
    public void testGetConfig() throws Exception {
        MoveletDataProviderBase provider = new MoveletDataProviderBase() {
            @Override
            public String getMoveletKeyExtension() {
                return "";
            }

            @Override
            public String getNamespace() {
                return "";
            }

            @Override
            public Date getValidTillDate() {
                return null;
            }
        };
        Assert.assertNotNull(provider.getConfig());
    }
}