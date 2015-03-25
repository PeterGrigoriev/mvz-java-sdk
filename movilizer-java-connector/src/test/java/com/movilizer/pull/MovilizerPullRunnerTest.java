package com.movilizer.pull;

import com.movilitas.movilizer.v12.MovilizerReplyMovelet;
import com.movilizer.acknowledgement.mock.MockMovilizerAcknowledgmentCall;
import com.movilizer.masterdata.EmptyMasterDataSource;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.masterdata.MasterdataAcknowledgementProcessor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class MovilizerPullRunnerTest {
    private MockMovilizerPullCall mockMovilizerPullCall;
    private MockMovilizerAcknowledgmentCall mockMovilizerAcknowledgmentCall;
    private Set<IReplyMoveletProcessor> replyMoveletProcessors;
    private MasterdataAcknowledgementProcessor masterdataAcknowledgementProcessor;
    private MockReplyProcessor firstReplyMoveletProcessor;
    private MockReplyProcessor secondReplyMoveletProcessor;

    @BeforeMethod
    public void setUp() throws Exception {
        mockMovilizerPullCall = new MockMovilizerPullCall();
        MovilizerReplyMovelet firstReplyMovelet = new MovilizerReplyMovelet();
        firstReplyMovelet.setMoveletKey("A");
        MovilizerReplyMovelet secondReplyMovelet = new MovilizerReplyMovelet();
        secondReplyMovelet.setMoveletKey("A");
        mockMovilizerPullCall.addReplyMovelet(firstReplyMovelet);
        mockMovilizerPullCall.addReplyMovelet(secondReplyMovelet);

        mockMovilizerAcknowledgmentCall = new MockMovilizerAcknowledgmentCall();

        firstReplyMoveletProcessor = new MockReplyProcessor("A", false);
        secondReplyMoveletProcessor = new MockReplyProcessor("B", false);
        replyMoveletProcessors = newHashSet((IReplyMoveletProcessor)firstReplyMoveletProcessor, secondReplyMoveletProcessor);

        masterdataAcknowledgementProcessor = new MasterdataAcknowledgementProcessor(new HashSet<IMasterdataXmlSetting>(), new EmptyMasterDataSource());
    }

    @Test
    public void testRun() throws Exception {
        new MovilizerPullRunner(mockMovilizerPullCall, mockMovilizerAcknowledgmentCall, replyMoveletProcessors, null, masterdataAcknowledgementProcessor).run();
        Assert.assertTrue(firstReplyMoveletProcessor.hasBeenCalled());
        Assert.assertTrue(firstReplyMoveletProcessor.getCount() == 2);
        Assert.assertFalse(secondReplyMoveletProcessor.hasBeenCalled());
    }
}