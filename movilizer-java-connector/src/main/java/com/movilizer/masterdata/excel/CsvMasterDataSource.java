package com.movilizer.masterdata.excel;

import com.google.gson.JsonArray;
import com.movilizer.masterdata.*;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;
import com.movilizer.util.resource.ResourceReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CsvMasterDataSource implements IMasterdataSource {
    private final ILogger logger = ComponentLogger.getInstance(CsvMasterDataSource.class);
    private final MasterdataJsonReader jsonReader;
    private final CsvToJsonConverter csvToJsonConverter;
    private final File csvFile;
    private int offset;

    public CsvMasterDataSource(File csvFile) {
        this(csvFile, 0);
    }

    public CsvMasterDataSource(File csvFile, int offset) {
        this.csvFile = csvFile;
        this.offset = offset;
        jsonReader = new MasterdataJsonReader();
        csvToJsonConverter = new CsvToJsonConverter();
    }


    @Override
    public IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        JsonArray jsonArray = csvToJsonConverter.convert(new FileReader(csvFile), setting.getFieldNames().getObjectId(), offset, setting.getLimit());
        offset += jsonArray.size();
        return jsonReader.readArray(jsonArray, setting);
    }

    @Override
    public void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException {

    }
}
