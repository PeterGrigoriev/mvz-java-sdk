package com.movilizer.masterdata;

import com.google.inject.ImplementedBy;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MasterdataDeletePool.class)
public interface IMasterdataDeletePool {
    void delete(String pool);
}
