package com.movilizer.rest;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockHttpClient implements HttpClient {
    private HttpContext context;
    private HttpHost target;
    private HttpRequest request;
    private ResponseHandler<?> responseHandler;

    HttpResponse response;

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    @Override
    public HttpParams getParams() {
        return null;
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return null;
    }

    @Override
    public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
        this.request = request;
        return response;
    }

    @Override
    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException, ClientProtocolException {
        this.request = request;
        this.context = context;
        return response;
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException, ClientProtocolException {
        this.target = target;
        this.request = request;
        return response;
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException, ClientProtocolException {
        this.target = target;
        this.request = request;
        this.context = context;
        return response;
    }

    @Override
    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        this.request = request;
        this.responseHandler = responseHandler;
        return null;
    }

    @Override
    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        this.request = request;
        this.responseHandler = responseHandler;
        this.context = context;
        return null;
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        this.target = target;
        this.request = request;
        this.responseHandler = responseHandler;
        return null;
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        this.target = target;
        this.request = request;
        this.responseHandler = responseHandler;
        this.context = context;
        return null;
    }


    public HttpContext getContext() {
        return context;
    }

    public HttpHost getTarget() {
        return target;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public ResponseHandler<?> getResponseHandler() {
        return responseHandler;
    }
}
