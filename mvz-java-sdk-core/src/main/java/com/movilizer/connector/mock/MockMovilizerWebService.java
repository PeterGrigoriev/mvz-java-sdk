package com.movilizer.connector.mock;

import com.movilitas.movilizer.v15.MovilizerRequest;
import com.movilitas.movilizer.v15.MovilizerResponse;
import com.movilitas.movilizer.v15.MovilizerWebServiceV15;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.EndpointReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMovilizerWebService implements MovilizerWebServiceV15, BindingProvider {
    private final Map<String, Object> requestContext = new HashMap<String, Object>();
    private final Map<String, Object> responseContext = new HashMap<String, Object>();
    private MovilizerResponse movilizerResponse;
    private MovilizerRequest lastRequest;

    public MockMovilizerWebService(MovilizerResponse movilizerResponse) {
        this.movilizerResponse = movilizerResponse;
    }

    public MockMovilizerWebService() {
        this(new MovilizerResponse());
    }


    @Override
    public Map<String, Object> getRequestContext() {
        return requestContext;
    }

    @Override
    public Map<String, Object> getResponseContext() {
        return responseContext;
    }

    @Override
    public Binding getBinding() {
        return null;
    }

    @Override
    public EndpointReference getEndpointReference() {
        return null;
    }

    @Override
    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz) {
        return null;
    }

    @Override
    public MovilizerResponse movilizer(MovilizerRequest movilizerRequest) {
        lastRequest = movilizerRequest;
        return movilizerResponse;
    }


    public MovilizerRequest getLastRequest() {
        return lastRequest;
    }
}
