package com.movilizer;

import com.movilizer.moveletbuilder.IMoveletDataProvider;
import com.movilizer.util.config.MovilizerConfig;
import com.movilizer.util.movelet.SimpleMoveletDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class SimpleMoveletDataProviderTest {

    private IMoveletDataProvider provider;

    @BeforeMethod
    public void setUp() throws Exception {
        provider = new SimpleMoveletDataProvider();

    }

    @Test
    public void testGetMoveletKeyExtension() throws Exception {
        assertNotNull(provider.getMoveletKeyExtension());
    }

    @Test
    public void testGetNamespace() throws Exception {
        assertNotNull(provider.getNamespace());
    }

    @Test
    public void testGetValidTillDate() throws Exception {
         assertNotNull(provider.getValidTillDate());
    }

    @Test
    public void testGetConfig() throws Exception {
        assertSame(provider.getConfig(), MovilizerConfig.getInstance());
    }
}
