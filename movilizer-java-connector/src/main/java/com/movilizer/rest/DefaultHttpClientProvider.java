package com.movilizer.rest;

import com.google.inject.Provider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class DefaultHttpClientProvider implements Provider<HttpClient> {

    @Override
    public HttpClient get() {
        return HttpClients.createDefault();
    }
}
