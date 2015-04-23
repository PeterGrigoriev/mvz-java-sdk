package com.movilizer.rest;


import com.google.gson.JsonPrimitive;
import com.google.inject.Provider;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class RestfulWebServiceTest  {

    private MockHttpClient client;
    private RestfulWebService service;

    public void testGetEndpoint() throws Exception {

    }

    @BeforeMethod
    public void setUp() throws Exception {
        client = new MockHttpClient();
        service = new RestfulWebService("a.b.com", new Provider<org.apache.http.client.HttpClient>() {
            @Override
            public org.apache.http.client.HttpClient get() {
                return client;
            }
        });

        client.setResponse(new BasicHttpResponse(new org.apache.http.ProtocolVersion("HTTP", 1, 1), 200, "OK"));

    }

    @Test
    public void testPut() throws Exception {
      service.put(new JsonPrimitive("Ma remontée terrain"));
        HttpRequest request = client.getRequest();
        assertNotNull(request);

    }


}