package com.movilizer.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Provider;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.apache.http.protocol.HTTP.CONTENT_TYPE;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class RestfulWebService {
    private final String endpoint;
    private final Provider<HttpClient> httpClientProvider;


    private static final ILogger logger = ComponentLogger.getInstance(RestfulWebService.class);

    public RestfulWebService(String endpoint, Provider<HttpClient> httpClientProvider) {
        this.endpoint = endpoint;
        this.httpClientProvider = httpClientProvider;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public JsonElement get() throws IOException {
        return get("");
    }

    public JsonElement get(String urlSuffix) throws IOException {
        Reader reader = getReader(urlSuffix);

        return new JsonParser().parse(reader);
    }

    public Reader getReader() throws IOException {
        return getReader("");
    }

    public Reader getReader(String urlSuffix) throws IOException {
        HttpClient httpClient = httpClientProvider.get();
        String uri = endpoint + urlSuffix;
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream inputStream = entity.getContent();

        logger.debug("Calling endpoint [" + uri + "]");
        return new InputStreamReader(inputStream);
    }

    public JsonElement get(int offset, int limit) throws IOException {
        return get("?offset=" + offset + "&limit=" + limit);
    }


    public JsonArray getArray(int offset, int limit) throws IOException {
        return get(offset, limit).getAsJsonArray();
    }

    public void put(JsonElement jsonElement) throws IOException {
        HttpClient httpClient = httpClientProvider.get();
        HttpPut httpPut = new HttpPut(endpoint);
        StringEntity stringEntity = new StringEntity(jsonElement.toString());
        stringEntity.setContentType(new BasicHeader(CONTENT_TYPE, "application/json"));
        httpPut.setEntity(stringEntity);
        HttpResponse response = httpClient.execute(httpPut);


        checkResponse(response);
    }

    public void post(JsonElement jsonElement) throws IOException {
        HttpClient httpClient = httpClientProvider.get();
        HttpPost httpPost = new HttpPost(endpoint);
        StringEntity stringEntity = new StringEntity(jsonElement.toString());
        stringEntity.setContentType(new BasicHeader(CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);
        HttpResponse response = httpClient.execute(httpPost);

        checkResponse(response);

    }

    private void checkResponse(HttpResponse response) throws HttpResponseException {
        StatusLine statusLine = response.getStatusLine();

        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(
                    statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
    }

    public Provider<Reader> asReaderProvider() {
        return new Provider<Reader>() {
            @Override
            public Reader get() {
                try {
                    return getReader("");
                } catch (IOException e) {
                    logger.error("Web service cannot get reader. Endpoint: [" + endpoint + "]", e);
                    return null;
                }
            }
        };
    }
}
