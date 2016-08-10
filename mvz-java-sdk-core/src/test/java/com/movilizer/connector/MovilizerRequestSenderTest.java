package com.movilizer.connector;

import com.movilitas.movilizer.v15.MovilizerRequest;
import com.movilizer.TestConstants;
import com.movilizer.connector.mock.MockMovilizerWebServiceProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.movilizer.TestConstants.TEST_PROXY;
import static org.testng.Assert.assertSame;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerRequestSenderTest {
    private MockMovilizerWebServiceProvider mockMovilizerWebServiceProvider;
    private MovilizerRequestSender requestSender;

    @BeforeMethod
    public void setUp() throws Exception {
        mockMovilizerWebServiceProvider = new MockMovilizerWebServiceProvider();
        requestSender = new MovilizerRequestSender(mockMovilizerWebServiceProvider);
    }

    @Test
    public void testSendRequest() throws Exception {
        MovilizerRequest request = new MovilizerRequest();
        requestSender.sendRequest(request, TestConstants.TEST_CLOUD_SYSTEM, TEST_PROXY);
        MovilizerRequest lastRequest = mockMovilizerWebServiceProvider.getLastRequest();
        assertSame(lastRequest, request);
    }
}
