package com.movilizer.masterdata;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMasterdataSource extends IMasterdataAcknowledger {
    IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException;
}
