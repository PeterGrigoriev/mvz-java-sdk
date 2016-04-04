package com.movilizer.masterdata.json;

import com.google.gson.JsonArray;
import com.google.inject.ImplementedBy;
import com.movilizer.masterdata.IMasterdataReader;
import com.movilizer.masterdata.IMasterdataReaderResult;
import com.movilizer.masterdata.IMasterdataXmlSetting;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MasterdataJsonReader.class)
public interface IMasterdataJsonReader extends IMasterdataReader {
    IMasterdataReaderResult readArray(JsonArray array, IMasterdataXmlSetting settings) throws IOException, XMLStreamException;
}
