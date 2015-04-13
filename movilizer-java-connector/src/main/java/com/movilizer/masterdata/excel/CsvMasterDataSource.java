package com.movilizer.masterdata.excel;

import com.google.gson.JsonArray;
import com.google.inject.Provider;
import com.movilitas.movilizer.v12.MovilizerMasterdataPoolUpdate;
import com.movilitas.movilizer.v12.MovilizerMasterdataReference;
import com.movilizer.masterdata.*;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.sql.SQLException;
import java.util.Collection;

import static com.movilizer.util.file.FileReaderProvider.newFileReaderProvider;

/**
 * @author Peter.Grigoriev@movilizer.com
 *
 */
public class CsvMasterDataSource implements IMasterdataSource {
    private final ILogger logger = ComponentLogger.getInstance(CsvMasterDataSource.class);
    private final MasterdataJsonReader jsonReader;
    private final CsvToJsonConverter csvToJsonConverter;

    private final Provider<Reader> readerProvider;
    private int offset;


    public CsvMasterDataSource(File csvFile) {
        this(csvFile, 0);
    }

    public CsvMasterDataSource(final File csvFile, int offset) {
        this(newFileReaderProvider(csvFile), offset);
    }


    public CsvMasterDataSource(Provider<Reader> readerProvider, int offset) {
        this.readerProvider = readerProvider;
        this.offset = offset;
        jsonReader = new MasterdataJsonReader();
        csvToJsonConverter = new CsvToJsonConverter();
    }

    public void setPostProcessPoolUpdate(Operation2<MovilizerMasterdataPoolUpdate, IMasterdataXmlSetting> postProcessPoolUpdate) {
        this.postProcessPoolUpdate = postProcessPoolUpdate;
    }

    private Operation2<MovilizerMasterdataPoolUpdate, IMasterdataXmlSetting> postProcessPoolUpdate = null;

    @Override
    public IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        JsonArray jsonArray = csvToJsonConverter.convert(readerProvider.get(), setting.getFieldNames().getObjectId(), offset, setting.getLimit());
        offset += jsonArray.size();
        IMasterdataReaderResult readerResult = jsonReader.readArray(jsonArray, setting);
        MovilizerMasterdataPoolUpdate poolUpdate = readerResult.getMasterdataPoolUpdate();
        if(null != postProcessPoolUpdate) {
            postProcessPoolUpdate.apply(poolUpdate, setting);
        }
        return readerResult;
    }

    @Override
    public void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException {

    }
}
