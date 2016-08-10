package com.movilizer.masterdata;

import com.movilitas.movilizer.v15.MovilizerMasterdataPoolUpdate;
import com.movilizer.TestConstants;
import com.movilizer.connector.mock.MockMovilizerRequestSender;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.push.MovilizerPushCall;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MasterdataDeletePoolTest {
    @Test
    public void testDelete() throws Exception {
        MockMovilizerRequestSender requestSender = new MockMovilizerRequestSender();
        IMovilizerPushCall pushCall = new MovilizerPushCall(TestConstants.TEST_CLOUD_SYSTEM, TestConstants.TEST_PROXY, requestSender, null, null);
        MasterdataDeletePool deletePool = new MasterdataDeletePool(pushCall);
        String pool = "TestPool";
        deletePool.delete(pool);
        List<MovilizerMasterdataPoolUpdate> masterdataPoolUpdates = requestSender.getLastSentRequest().getMasterdataPoolUpdate();
        assertEquals(masterdataPoolUpdates.size(), 1);
        MovilizerMasterdataPoolUpdate update = masterdataPoolUpdates.get(0);
        assertEquals(update.getPool(), pool);


    }
}
