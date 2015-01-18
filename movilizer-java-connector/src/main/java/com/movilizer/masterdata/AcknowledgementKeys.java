package com.movilizer.masterdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class AcknowledgementKeys implements IAcknowledgementKeys {
    private final IMasterdataXmlSetting setting;
    private final List<Integer> keys;

    public AcknowledgementKeys(IMasterdataXmlSetting setting) {
        this.setting = setting;
        this.keys = new ArrayList<Integer>();
    }

    @Override
    public void add(Integer key) {
        keys.add(key);
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public Collection<Integer> getKeys() {
        return keys;
    }

    @Override
    public IMasterdataXmlSetting getSetting() {
        return setting;
    }
}
