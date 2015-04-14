package com.movilizer.masterdata;

import com.movilizer.rest.INamedReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class JsonMasterDataSource implements IMasterdataSource {
    private final INamedReaderProvider jsonSource;
    private final MasterdataJsonReader reader;

    public JsonMasterDataSource(INamedReaderProvider jsonSource) {
        this.jsonSource = jsonSource;
        reader = new MasterdataJsonReader();
    }

    @Override
    public IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        String pool = setting.getPool();
        return reader.read(jsonSource.get(pool + ".json").get(), setting);
    }

    @Override
    public void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException {
    }
}
