package com.movilizer.push;

import com.google.common.base.Function;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.movilitas.movilizer.v15.MovilizerRequest;
import com.movilizer.util.movelet.SimpleMoveletDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerPushCallPingTest {

    private IMovilizerPushCall pushCall;

    @BeforeMethod
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new MovilizerPushCallModule());
        pushCall = injector.getInstance(IMovilizerPushCall.class);


    }

    //
    @Test(enabled = false)
    public void testPing() {
        System.out.println("TESTING PING");
        boolean success = pushCall.send();
        assertTrue(success);
    }

    @Test
    public void testDeletionIsAddedWhenNoVersionIsThere() throws Exception {
        pushCall.addMovelets(new SimpleMoveletDataProvider(), "WelcomeMovelet.vm");
        pushCall.doWithRequest(new Function<MovilizerRequest, Void>() {
            public Void apply(MovilizerRequest movilizerRequest) {
                Assert.assertEquals(movilizerRequest.getMoveletDelete().size(), 1);
                return null;
            }
        });
    }

    @Test
    public void testDeletionIsAddedWhenVersionIsThere() throws Exception {
        pushCall.addMovelets(new SimpleMoveletDataProvider(), "WelcomeMoveletWithVersion.vm");
        pushCall.doWithRequest(new Function<MovilizerRequest, Void>() {
            public Void apply(MovilizerRequest movilizerRequest) {
                Assert.assertEquals(movilizerRequest.getMoveletDelete().size(), 0);
                return null;
            }
        });
    }

}
