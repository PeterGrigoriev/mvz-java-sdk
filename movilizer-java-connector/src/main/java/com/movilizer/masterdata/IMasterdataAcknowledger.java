package com.movilizer.masterdata;

import com.google.inject.ImplementedBy;

import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(EmptyMasterDataSource.class)
public interface IMasterdataAcknowledger {
    void acknowledge(IMasterdataXmlSetting setting, Collection<Integer> eventIds, AcknowledgementStatus status) throws SQLException;
}
