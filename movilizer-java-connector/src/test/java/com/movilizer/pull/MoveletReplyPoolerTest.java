package com.movilizer.pull;

import com.movilitas.movilizer.v12.MovilizerReplyMovelet;
import com.movilizer.acknowledgement.mock.MockMovilizerAcknowledgmentCall;
import com.movilizer.masterdata.EmptyMasterDataSource;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.masterdata.MasterdataAcknowledgementProcessor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;

import static com.google.common.collect.Sets.newHashSet;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MoveletReplyPoolerTest {

    public static final HashSet<IMovilizerResponseObserver> NO_RESPONSE_OBSERVERS = new HashSet<IMovilizerResponseObserver>();
    public static final MasterdataAcknowledgementProcessor ACKNOWLEDGEMENT_PROCESSOR = new MasterdataAcknowledgementProcessor(new HashSet<IMasterdataXmlSetting>(), new EmptyMasterDataSource());
    private MockMovilizerAcknowledgmentCall acknowledgmentCall;

    @BeforeMethod
    public void setUp() throws Exception {
        acknowledgmentCall = new MockMovilizerAcknowledgmentCall();
    }

    @Test
    public void testRunWithNoExceptions() throws Exception {
        MovilizerReplyMovelet replyMovelet = new MovilizerReplyMovelet();
        replyMovelet.setMoveletKey("X1_someMovelet1");
        MockMovilizerPullCall callForReplies = new MockMovilizerPullCall();
        callForReplies.addReplyMovelet(replyMovelet);

        MockReplyProcessor processor = new MockReplyProcessor("X1_", false);

        MovilizerPullRunner pooler = new MovilizerPullRunner(callForReplies, acknowledgmentCall, newHashSet((IReplyMoveletProcessor)processor), null, ACKNOWLEDGEMENT_PROCESSOR);

        boolean success = pooler.run();
        assertTrue(processor.hasBeenCalled());
        assertTrue(success);
        assertTrue(acknowledgmentCall.hasAcknowledgmentBeenCalled());

    }

    @Test
    public void testRunWithException() throws Exception {
        MovilizerReplyMovelet replyMovelet = new MovilizerReplyMovelet();
        replyMovelet.setMoveletKey("X1_someMovelet1");
        MockMovilizerPullCall mockCallForReplies = new MockMovilizerPullCall();
        mockCallForReplies.addReplyMovelet(replyMovelet);
        MockReplyProcessor processor = new MockReplyProcessor("X1_", true);

        MovilizerPullRunner pooler = new MovilizerPullRunner(mockCallForReplies, acknowledgmentCall, newHashSet((IReplyMoveletProcessor)processor), null, ACKNOWLEDGEMENT_PROCESSOR);
        boolean success = pooler.run();
        assertTrue(processor.hasBeenCalled());
        assertFalse(success);
        assertFalse(acknowledgmentCall.hasAcknowledgmentBeenCalled());
    }
}
