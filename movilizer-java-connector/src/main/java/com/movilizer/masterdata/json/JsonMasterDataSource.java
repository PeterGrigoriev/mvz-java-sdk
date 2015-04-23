package com.movilizer.masterdata.json;

import com.movilizer.masterdata.AcknowledgementStatus;
import com.movilizer.masterdata.IMasterdataReaderResult;
import com.movilizer.masterdata.IMasterdataSource;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.rest.INamedReaderProvider;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class JsonMasterDataSource implements IMasterdataSource {
    private final INamedReaderProvider jsonSource;
    private final MasterdataJsonReader jsonReader;

    private final ILogger logger = ComponentLogger.getInstance(JsonMasterDataSource.class);

    public JsonMasterDataSource(INamedReaderProvider jsonSource) {
        this.jsonSource = jsonSource;
        jsonReader = new MasterdataJsonReader();
    }

    @Override
    public IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        String pool = setting.getPool();
        Reader reader = jsonSource.get(pool + ".json").get();
        if(reader == null) {
            logger.error("Could not obtain reader for the pool [" + setting.getPool() + "]");
            return null;
        }
        return jsonReader.read(reader, setting);
    }

    @Override
    public void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException {

    }
}

