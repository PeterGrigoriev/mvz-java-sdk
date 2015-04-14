package com.movilizer.masterdata.csv;

import com.google.common.base.Predicate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.inject.Provider;
import com.movilitas.movilizer.v12.MovilizerMasterdataPoolUpdate;
import com.movilizer.masterdata.AcknowledgementStatus;
import com.movilizer.masterdata.IMasterdataReaderResult;
import com.movilizer.masterdata.IMasterdataSource;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.masterdata.json.MasterdataJsonReader;
import com.movilizer.util.functional.Operation2;
import com.movilizer.util.json.JsonUtils;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Collection;

import static com.movilizer.util.file.FileReaderProvider.newFileReaderProvider;
import static com.movilizer.util.json.JsonUtils.ACCEPT_ALL;
import static com.movilizer.util.json.JsonUtils.isJsonObjectAnd;

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


    private Predicate<JsonElement> filter = ACCEPT_ALL;

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


    public void setFilter(Predicate<JsonObject> filter) {
        this.filter = isJsonObjectAnd(filter);
    }

    private Operation2<MovilizerMasterdataPoolUpdate, IMasterdataXmlSetting> postProcessPoolUpdate = null;


    @Override
    public IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        JsonArray jsonArray = csvToJsonConverter.convert(readerProvider.get(), setting.getFieldNames().getObjectId(), offset, setting.getLimit());
        offset += jsonArray.size();
        jsonArray = JsonUtils.filter(jsonArray, filter);
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
