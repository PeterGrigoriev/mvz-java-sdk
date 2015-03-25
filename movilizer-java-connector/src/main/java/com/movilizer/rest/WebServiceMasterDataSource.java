package com.movilizer.rest;

import com.google.inject.Provider;
import com.movilizer.masterdata.*;
import org.apache.http.client.HttpClient;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class WebServiceMasterDataSource implements IMasterdataSource {
    private final Provider<HttpClient> httpClientProvider;

    public WebServiceMasterDataSource(Map<String, String> pollToGetEndPoint, Provider<HttpClient> httpClientProvider) {
        this.httpClientProvider = httpClientProvider;
        this.pollToGetEndPoint = pollToGetEndPoint;
    }

    @Override
    public IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        return getJsonMasterDataSource().read(setting);
    }

    @Override
    public void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException {
        getJsonMasterDataSource().acknowledge(setting, eventIds, status);
    }

    public JsonMasterDataSource getJsonMasterDataSource() {
        if(null == jsonMasterDataSource) {
            jsonMasterDataSource = createJsonMasterDataSource();
        }
        return jsonMasterDataSource;
    }

    private JsonMasterDataSource createJsonMasterDataSource() {
        return new JsonMasterDataSource(getNamedReaderProvider());
    }

    protected INamedReaderProvider getNamedReaderProvider() {
        
        return new RestfulWebServiceBundle(pollToGetEndPoint, httpClientProvider);
    }

    private JsonMasterDataSource jsonMasterDataSource;
    private final Map<String, String> pollToGetEndPoint;
}
