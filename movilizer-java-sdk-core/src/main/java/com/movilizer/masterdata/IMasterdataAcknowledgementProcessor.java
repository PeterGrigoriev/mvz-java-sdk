package com.movilizer.masterdata;


import com.google.inject.ImplementedBy;
import com.movilitas.movilizer.v14.MovilizerMasterdataAck;
import com.movilitas.movilizer.v14.MovilizerMasterdataDeleted;
import com.movilitas.movilizer.v14.MovilizerMasterdataError;
import com.movilizer.pull.CannotProcessMasterdataAcknowledgementException;
import com.movilizer.pull.CannotProcessMasterdataDeletionException;
import com.movilizer.pull.CannotSubmitMasterdataAcknowledgementsException;

import java.util.Set;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
@ImplementedBy(MasterdataAcknowledgementProcessor.class)
public interface IMasterdataAcknowledgementProcessor {
    Set<String> getTargetPools();

    void processMasterdataAcknowledgement(MovilizerMasterdataAck masterdataAcknowledgement) throws CannotProcessMasterdataAcknowledgementException;

    void processMasterdataDeletion(MovilizerMasterdataDeleted delete) throws CannotProcessMasterdataDeletionException;

    void processMasterdataError(MovilizerMasterdataError masterdataError) throws CannotProcessMasterdataAcknowledgementException;

    void submit() throws CannotSubmitMasterdataAcknowledgementsException;


}
