package com.movilizer.connector.mock;

import com.movilitas.movilizer.v14.MovilizerRequest;
import com.movilitas.movilizer.v14.MovilizerResponse;
import com.movilitas.movilizer.v14.MovilizerWebServiceV14;
import com.movilizer.connector.IMovilizerWebServiceProvider;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMovilizerWebServiceProvider implements IMovilizerWebServiceProvider {

    private final MockMovilizerWebService service;
    public MockMovilizerWebServiceProvider(MovilizerResponse response) {
        service = new MockMovilizerWebService(response);
    }

    public MockMovilizerWebServiceProvider() {
        this(new MovilizerResponse());
    }

    @Override
    public MovilizerWebServiceV14 get() {
        return service;
    }

    public MovilizerRequest getLastRequest() {
        return service.getLastRequest();
    }

    public MockMovilizerWebService getService() {
        return service;
    }
}
