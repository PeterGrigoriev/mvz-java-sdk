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
            RestfulWebService webService = new RestfulWebService(endpoint, httpClientProvider);
            nameToWebService.put(name, webService);
            nameToWebService.put(name + ".json", webService);
        }
    }

    @Override
    public Provider<Reader> get(String name) {
        RestfulWebService webService = nameToWebService.get(name);
        return webService.asReaderProvider();
    }
}
