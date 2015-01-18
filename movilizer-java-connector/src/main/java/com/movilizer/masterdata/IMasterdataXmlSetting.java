package com.movilizer.masterdata;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMasterdataXmlSetting {
    String getPool();

    String getTargetPool();

    boolean isReference();

    IMasterdataFieldNames getFieldNames();

    int getLimit();

    String getSubscriber();

    int getNumberOfLoops();

}
