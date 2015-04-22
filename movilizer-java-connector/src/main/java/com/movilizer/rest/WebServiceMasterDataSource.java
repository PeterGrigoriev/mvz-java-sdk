package com.movilizer.rest;

import com.google.inject.Provider;
import com.movilizer.masterdata.AcknowledgementStatus;
import com.movilizer.masterdata.IMasterdataReaderResult;
import com.movilizer.masterdata.IMasterdataSource;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.masterdata.json.JsonMasterDataSource;
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
    private final Map<String, String> pollToGetAcknowledgeEndPoint;
    protected final Provider<HttpClient> httpClientProvider;

    public WebServiceMasterDataSource(Map<String, String> pollToGetEndPoint, Map<String, String> pollToAcknowledgeEndPoint,  Provider<HttpClient> httpClientProvider) {
        this.pollToGetEndPoint = pollToGetEndPoint;
        this.pollToGetAcknowledgeEndPoint = pollToAcknowledgeEndPoint;
        this.httpClientProvider = httpClientProvider;
    }

    @Override
    public IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        return getJsonMasterDataSource().read(setting);
    }

    @Override
    public void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException {
        String acknowledgementEndpoint = getAcknowledgementEndpoint(setting);
        WebServiceEventAcknowledger acknowledger = new WebServiceEventAcknowledger(acknowledgementEndpoint, httpClientProvider);
        try {
            acknowledger.acknowledge(eventIds, status.toEventAcknowledgementStatus());
        } catch (Exception e) {
            throw new SQLException("Acknowledgement with Web Service failed", e);
        }
    }

    public String getAcknowledgementEndpoint(IMasterdataXmlSetting setting) {
        return pollToGetAcknowledgeEndPoint.get(setting.getPool());
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
