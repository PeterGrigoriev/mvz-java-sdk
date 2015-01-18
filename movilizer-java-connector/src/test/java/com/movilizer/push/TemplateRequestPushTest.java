package com.movilizer.push;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.movilizer.TestModule;
import com.movilizer.connector.mock.MockMovilizerRequestSender;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class TemplateRequestPushTest {

    private MockMovilizerRequestSender requestSender;
    private ITemplateRequestPush templateRequestPush;

    @BeforeMethod
    public void setUp() throws Exception {
        requestSender = new MockMovilizerRequestSender();
        Injector injector = Guice.createInjector(new TestModule(requestSender));
        templateRequestPush = injector.getInstance(ITemplateRequestPush.class);

    }

    // TODO: implement tests
    @Test
    public void testPush() throws Exception {
       // templateRequestPush.push();
    }

    @Test
    public void testCreateRequest() throws Exception {

    }
}
