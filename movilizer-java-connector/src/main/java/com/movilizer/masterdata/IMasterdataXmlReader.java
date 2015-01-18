package com.movilizer.masterdata;

import com.google.inject.ImplementedBy;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MasterdataXmlReader.class)
public interface IMasterdataXmlReader extends IMasterdataReader {
}
