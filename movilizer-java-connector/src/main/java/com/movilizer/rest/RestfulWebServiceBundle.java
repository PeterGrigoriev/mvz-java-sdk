package com.movilizer.rest;

import com.google.inject.Provider;
import org.apache.http.client.HttpClient;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class RestfulWebServiceBundle implements INamedReaderProvider {
    private final Map<String, RestfulWebService> nameToWebService = new HashMap<String, RestfulWebService>();

    public RestfulWebServiceBundle(Map<String, String> nameToEndPoint, Provider<HttpClient> httpClientProvider) {
        for (Map.Entry<String, String> entry : nameToEndPoint.entrySet()) {
            final String name = entry.getKey();
            final String endpoint = entry.getValue();
            nameToWebService.put(name, new RestfulWebService(endpoint, httpClientProvider));
        }
    }

    @Override
    public Provider<Reader> get(String name) {
        return nameToWebService.get(name).asReaderProvider();
    }
}
