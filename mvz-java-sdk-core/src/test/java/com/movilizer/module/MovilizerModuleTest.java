package com.movilizer.module;

import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.movilitas.movilizer.v15.MovilizerReplyMovelet;
import com.movilizer.pull.CannotProcessReplyMoveletException;
import com.movilizer.pull.IMovilizerPullCall;
import com.movilizer.pull.IReplyMoveletProcessor;
import com.movilizer.util.config.MovilizerConfig;
import com.movilizer.util.proxy.ProxyInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertNotNull;

public class MovilizerModuleTest {



    @Test
    public void testConfigureProxy() throws Exception {
        MovilizerConfig config = (MovilizerConfig) MovilizerConfig.getInstance();
        config.setProxyInfo(null);

        MovilizerModule module = new MovilizerModule() {
            @Override
            protected void setUp() {
            }
        };

        assertNotNull(getBinding(module, IMovilizerPullCall.class));

        config.setProxyInfo(new ProxyInfo("", ""));

        assertNotNull(getBinding(module, IMovilizerPullCall.class));
    }



    @Test
    public void testAddReplyProcessor() {
        final IReplyMoveletProcessor replyMoveletProcessor = new IReplyMoveletProcessor() {
            @Override
            public boolean canProcessReplyMovelet(MovilizerReplyMovelet replyMovelet) {
                return false;
            }

            @Override
            public void processReplyMovelet(MovilizerReplyMovelet replyMovelet) throws CannotProcessReplyMoveletException {
            }
        };
        MovilizerModule module = new MovilizerModule() {
            @Override
            protected void setUp() {
                addReplyProcessor(replyMoveletProcessor);
            }
        };
        Set<IReplyMoveletProcessor> replyMoveletProcessors = getSetInstance(module, new TypeLiteral<Set<IReplyMoveletProcessor>>(){});

        Assert.assertFalse(replyMoveletProcessors.isEmpty());
        Assert.assertTrue(replyMoveletProcessors.contains(replyMoveletProcessor));

    }

    private static <T> T getBinding(MovilizerModule myModule, Class<T> aClass) {
        return Guice.createInjector(myModule).getInstance(aClass);
    }

    private <T> Set<T> getSetInstance(MovilizerModule module, TypeLiteral<Set<T>> type) {
        return Guice.createInjector(module).getInstance(Key.get(type));
    }

}