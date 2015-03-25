package com.movilizer.acknowledgement;

import com.movilitas.movilizer.v12.MovilizerRequest;
import com.movilitas.movilizer.v12.MovilizerResponse;
import com.movilizer.connector.mock.MockMovilizerRequestSender;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.movilizer.TestConstants.TEST_CLOUD_SYSTEM;
import static com.movilizer.TestConstants.TEST_PROXY;
import static org.testng.Assert.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerAcknowledgmentCallTest {
    private MockMovilizerRequestSender requestSender;
    private MovilizerAcknowledgmentCall acknowledgementCall;

    @BeforeMethod
    public void setUp() throws Exception {
        requestSender = new MockMovilizerRequestSender();
        acknowledgementCall = new MovilizerAcknowledgmentCall(TEST_CLOUD_SYSTEM, TEST_PROXY, requestSender);
    }

    @Test
    public void testGetNumberOfRepliesToReceive() throws Exception {
        assertEquals(acknowledgementCall.getNumberOfRepliesToReceive(), 0);
    }

    @Test
    public void testAcknowledgeResponse() throws Exception {
        MovilizerResponse response = new MovilizerResponse();
        String requestAcknowledgeKey = "TEST_KEY";
        response.setRequestAcknowledgeKey(requestAcknowledgeKey);
        acknowledgementCall.acknowledgeResponse(response);
        MovilizerRequest lastSentRequest = requestSender.getLastSentRequest();
        assertNotNull(lastSentRequest);
        assertEquals(lastSentRequest.getRequestAcknowledgeKey(), requestAcknowledgeKey);
    }

    @Test
    public void testAcknowledgeResponseWithNullKey() {
        MovilizerResponse response = new MovilizerResponse();
        response.setRequestAcknowledgeKey(null);
        acknowledgementCall.acknowledgeResponse(response);
        assertNull(requestSender.getLastSentRequest());
    }

}
