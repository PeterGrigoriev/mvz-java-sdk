package com.movilizer.masterdata;

import com.google.inject.ImplementedBy;
import com.movilitas.movilizer.v11.MovilizerMasterdataPoolUpdate;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MasterdataUpdateBuilder.class)
public interface IMasterdataUpdateBuilder {
    MovilizerMasterdataPoolUpdate build(IMasterdataUpdateInfoProvider provider, String templateName) throws Exception;
}
