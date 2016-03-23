package com.movilizer.masterdata;

import com.google.inject.Inject;
import com.movilitas.movilizer.v14.MovilizerMasterdataAck;
import com.movilitas.movilizer.v14.MovilizerMasterdataDeleted;
import com.movilitas.movilizer.v14.MovilizerMasterdataError;
import com.movilitas.movilizer.v14.MovilizerResponse;
import com.movilizer.pull.*;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MasterDataResponseObserver implements IMovilizerResponseObserver{
    private final IMasterdataAcknowledgementProcessor masterdataAcknowledgementProcessor;

    private final static ILogger logger = ComponentLogger.getInstance(MasterDataResponseObserver.class);

    @Inject
    public MasterDataResponseObserver(IMasterdataAcknowledgementProcessor masterdataAcknowledgementProcessor) {
        this.masterdataAcknowledgementProcessor = masterdataAcknowledgementProcessor;
    }


    @Override
    public void onResponseAvailable(MovilizerResponse movilizerResponse) throws KeepItOnTheCloudException {
        List<MovilizerMasterdataAck> masterdataAcks = movilizerResponse.getMasterdataAck();
        List<MovilizerMasterdataDeleted> masterdataDels = movilizerResponse.getMasterdataDeleted();


        List<MovilizerMasterdataError> masterdataErrors = movilizerResponse.getMasterdataError();

        if(masterdataAcks.isEmpty() && masterdataErrors.isEmpty() && masterdataDels.isEmpty()) {
            return;
        }

        Set<String> unknownPools = new HashSet<String>();
        boolean success = processMasterdataAcksAndDeletes(masterdataAcks, masterdataDels, unknownPools);

        success &= processMasterdataErrors(masterdataErrors, unknownPools);

        if (!unknownPools.isEmpty()) {
            for (String unknownPool : unknownPools) {
                logger.error("Unknown pool: [" + unknownPool + "]. No Masterdata Acknowledgement Processor registered.");
            }
        }
        success &= submitMasterdataAcknowledgements();
        if(!success) {
            throw new KeepItOnTheCloudException();
        }
    }

    private boolean processMasterdataErrors(List<MovilizerMasterdataError> movilizerMasterdataErrors, Set<String> unknownPools) {
        if(masterdataAcknowledgementProcessor == null) {
            return false;
        }
        boolean success = true;
        for (MovilizerMasterdataError error : movilizerMasterdataErrors) {
            String pool = error.getPool();
            if (!canAcknowledge(pool)) {
                unknownPools.add(pool);
            } else {
                try {
                    masterdataAcknowledgementProcessor.processMasterdataError(error);
                } catch (CannotProcessMasterdataAcknowledgementException e) {
                    logger.error(e);
                    success = false;
                }
            }
        }
        return success;
    }

    private boolean processMasterdataAcksAndDeletes(List<MovilizerMasterdataAck> masterdataAcks, List<MovilizerMasterdataDeleted> masterdataDels, Set<String> unknownPools) {

        if(masterdataAcknowledgementProcessor == null) {
            return false;
        }

        boolean success = processAcknowledgements(masterdataAcks, unknownPools);
        success &= processMasterdataDeletes(masterdataDels, unknownPools);
        return success;
    }

    private boolean processMasterdataDeletes(List<MovilizerMasterdataDeleted> masterdataDels, Set<String> unknownPools) {
        boolean success = true;
        for (MovilizerMasterdataDeleted delete : masterdataDels) {
            String pool = delete.getPool();
            if (!canAcknowledge(pool)) {
                unknownPools.add(pool);
                continue;
            }

            try {
                masterdataAcknowledgementProcessor.processMasterdataDeletion(delete);
            } catch (CannotProcessMasterdataDeletionException e) {
                logger.error(e);
                success = false;
            }
        }
        return success;
    }

    private boolean processAcknowledgements(List<MovilizerMasterdataAck> masterdataAcks, Set<String> unknownPools) {
        boolean success = true;
        for (MovilizerMasterdataAck masterdataAck : masterdataAcks) {
            String pool = masterdataAck.getPool();
            if (!canAcknowledge(pool)) {
                unknownPools.add(pool);
                continue;
            }


            try {
                masterdataAcknowledgementProcessor.processMasterdataAcknowledgement(masterdataAck);
            } catch (CannotProcessMasterdataAcknowledgementException e) {
                logger.error(e);
                success = false;
            }
        }
        return success;
    }

    private boolean submitMasterdataAcknowledgements() {
        try {
            if(null == masterdataAcknowledgementProcessor) {
                return false;
            }
            masterdataAcknowledgementProcessor.submit();
        } catch (CannotSubmitMasterdataAcknowledgementsException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    private boolean canAcknowledge(String pool) {
        return masterdataAcknowledgementProcessor != null && masterdataAcknowledgementProcessor.getTargetPools().contains(pool);
    }


}
