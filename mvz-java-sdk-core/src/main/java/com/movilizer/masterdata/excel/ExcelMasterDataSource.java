package com.movilizer.masterdata.excel;

import com.google.gson.JsonArray;
import com.movilizer.masterdata.*;
import com.movilizer.masterdata.json.MasterdataJsonReader;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ExcelMasterDataSource implements IMasterdataSource {
    private final ILogger logger = ComponentLogger.getInstance(ExcelMasterDataSource.class);
    private final MasterdataJsonReader jsonReader;
    private final ExcelToJsonConverter excelToJsonConverter;
    private final InputStream inputStream;

    public ExcelMasterDataSource(InputStream inputStream) {
        this.inputStream = inputStream;
        jsonReader = new MasterdataJsonReader();
        excelToJsonConverter = new ExcelToJsonConverter();
    }




    @Override
    public IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        String pool = setting.getPool();
        logger.debug("Reading data for pool [" + pool + "]");
        JsonArray jsonArray = excelToJsonConverter.convert(inputStream, setting.getFieldNames().getObjectId());

        IMasterdataReaderResult readResult = jsonReader.readArray(jsonArray, setting);
        if(null == readResult) {
            logger.debug("No results read for pool [" + pool + "]");
            return null;
        }
        logger.debug("There were [" + readResult.getMasterdataPoolUpdate().getUpdate().size() + "] updates for pool [" + pool + "]");
        return readResult;
    }

    @Override
    public void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException {

    }
}
