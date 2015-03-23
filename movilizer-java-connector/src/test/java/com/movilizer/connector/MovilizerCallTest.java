package com.movilizer.connector;

import com.google.common.base.Function;
import com.movilitas.movilizer.v12.MovilizerMovelet;
import com.movilitas.movilizer.v12.MovilizerMoveletSet;
import com.movilitas.movilizer.v12.MovilizerParticipant;
import com.movilitas.movilizer.v12.MovilizerRequest;
import com.movilizer.connector.mock.MockMovilizerRequestSender;
import com.movilizer.document.MovilizerDocumentUploader;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.push.MovilizerPushCall;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.MovilizerConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.movilizer.usermanagement.MockMovilizerUserManager.getSomeUser;
import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

/**
 * @author Peter.Grigoriev@movilizer.com
 */


public class MovilizerCallTest {
    private IMovilizerPushCall movilizerCall;

    public MovilizerCallTest() {
    }

    @BeforeMethod
    public void setUp() throws Exception {
        MockMovilizerRequestSender requestSender = new MockMovilizerRequestSender();
        IMovilizerConfig config = MovilizerConfig.getInstance();
        movilizerCall = new MovilizerPushCall(config.getMovilizerSystem(),
                config.getProxyInfo(),
                requestSender,
                new MovilizerDocumentUploader(), null);
    }

    @Test
    public void testAddToRequest() throws Exception {
        MovilizerMovelet movelet = new MovilizerMovelet();
        movelet.setMoveletKey("SomeKey");
        movilizerCall.addMovelet(movelet, new ArrayList<IMovilizerUser>());
        movilizerCall.doWithRequest(new Function<MovilizerRequest, Void>() {
            @Override
            public Void apply(MovilizerRequest request) {
                MovilizerMoveletSet moveletSet = request.getMoveletSet().get(0);
                MovilizerMovelet retrievedMovelet = moveletSet.getMovelet().get(0);
                assertEquals("SomeKey", retrievedMovelet.getMoveletKey());
                return null;
            }
        });
    }

    @Test
    public void testAddMoveletWithParticipant() throws Exception {
        MovilizerMovelet movelet = new MovilizerMovelet();
        movelet.setMoveletKey("SomeKey");
        final IMovilizerUser user = getSomeUser();
        movilizerCall.addMovelet(movelet, asList(user));

        movilizerCall.doWithRequest(new Function<MovilizerRequest, Void>() {
            @Override
            public Void apply(MovilizerRequest request) {
                MovilizerMoveletSet moveletSet = request.getMoveletSet().get(0);
                MovilizerParticipant movilizerParticipant = moveletSet.getParticipant().get(0);
                Assert.assertEquals(user.getDeviceAddress(), movilizerParticipant.getDeviceAddress());
                return null;
            }

        });
    }



    @Test
    public void testSend() throws Exception {
        movilizerCall.send();
    }
}
