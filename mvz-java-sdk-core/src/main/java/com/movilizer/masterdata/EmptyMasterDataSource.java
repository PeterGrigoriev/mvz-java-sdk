package com.movilizer.masterdata;

import com.google.inject.Singleton;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.stream.XMLStreamException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class EmptyMasterDataSource implements IMasterdataSource {
    private final ILogger logger = ComponentLogger.getInstance("MasterData");

    @Override
    public IMasterdataReaderResult read(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException {
        logger.warn("Trying to read pool [" + setting.getPool() + "] using the EmptyMasterDataSource.");
        return null;
    }

    @Override
    public void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException {
        logger.warn("Trying to acknowledge masterdata from pool [" + setting.getPool() + "] using the EmptyMasterDataSource.");
    }
}
