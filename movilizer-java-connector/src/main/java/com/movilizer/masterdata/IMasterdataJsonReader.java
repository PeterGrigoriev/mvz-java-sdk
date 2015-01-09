package com.movilizer.masterdata;

import com.google.inject.ImplementedBy;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MasterdataJsonReader.class)
public interface IMasterdataJsonReader extends IMasterdataReader {
}
