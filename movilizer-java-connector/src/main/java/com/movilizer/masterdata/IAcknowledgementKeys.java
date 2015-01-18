package com.movilizer.masterdata;

import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IAcknowledgementKeys {
    Collection<Integer> getKeys();

    IMasterdataXmlSetting getSetting();

    void add(Integer key);

    int size();
}
