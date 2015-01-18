package com.movilizer.masterdata;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Peter.Grigoriev@movilizer.com.
 */
public interface IMasterdataReader {
    IMasterdataReaderResult read(Reader xmlReader, IMasterdataXmlSetting settings) throws IOException, XMLStreamException;
}
