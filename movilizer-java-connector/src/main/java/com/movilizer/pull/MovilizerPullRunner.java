package com.movilizer.pull;


import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.movilitas.movilizer.v11.*;
import com.movilizer.acknowledgement.IMovilizerAcknowledgementCall;
import com.movilizer.connector.MovilizerCallResult;
import com.movilizer.masterdata.IMasterdataAcknowledgementProcessor;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.*;

import static com.movilizer.util.collection.CollectionUtils.nullToEmptySet;
import static java.text.MessageFormat.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */

@Singleton
public class MovilizerPullRunner implements IMovilizerPullRunner {

    private final ILogger logger = ComponentLogger.getInstance("MovilizerPullRunner");

    protected final IMovilizerPullCall pullCall;
    protected final IMovilizerAcknowledgementCall acknowledgementCall;
    protected final Set<IReplyMoveletProcessor> replyProcessors;
    private final IMasterdataAcknowledgementProcessor masterdataAcknowledgementProcessor;
    protected final Set<IMovilizerResponseObserver> responseObservers;


    @Inject
    public MovilizerPullRunner(IMovilizerPullCall pullCall, IMovilizerAcknowledgementCall acknowledgementCall,
                               Set<IReplyMoveletProcessor> replyProcessors,
                               Set<IMovilizerResponseObserver> responseObservers,
                               IMasterdataAcknowledgementProcessor masterdataAcknowledgementProcessor) {
        this.pullCall = pullCall;
        this.acknowledgementCall = acknowledgementCall;
        this.replyProcessors = nullToEmptySet(replyProcessors);
        this.masterdataAcknowledgementProcessor = masterdataAcknowledgementProcessor;
        this.responseObservers = nullToEmptySet(responseObservers);
    }


    public boolean processMasterdataAcknowledgements(MovilizerResponse movilizerResponse) {
        List<MovilizerMasterdataAck> masterdataAcks = movilizerResponse.getMasterdataAck();
        List<MovilizerMasterdataDeleted> masterdataDels = movilizerResponse.getMasterdataDeleted();


        List<MovilizerMasterdataError> masterdataErrors = movilizerResponse.getMasterdataError();

        if(masterdataAcks.isEmpty() && masterdataErrors.isEmpty() && masterdataDels.isEmpty()) {
            return true;
        }

        Set<String> unknownPools = new HashSet<String>();
        boolean success = processMasterdataAcksAndDeletes(masterdataAcks, masterdataDels, unknownPools);

        success &= processMasterdataErrors(masterdataErrors, unknownPools);

        if (!unknownPools.isEmpty()) {
            for (String unknownPool : unknownPools) {
                logger.error("Unknown pool: [" + unknownPool + "]. No Masterdata Acknowledgement Processor registered.");
            }
        }
        return submitMasterdataAcknowledgements() && success;
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

    protected boolean processReplyMovelets(MovilizerResponse response) {
        logResponse(response);

        Map<IReplyMoveletProcessor, List<ReplyMovelet>> map = getProcessorToReplyMoveletListMap(replyProcessors, response.getReplyMovelet());

        boolean success = true;
        for (IReplyMoveletProcessor replyMoveletProcessor : map.keySet()) {
            List<ReplyMovelet> replyMovelets = map.get(replyMoveletProcessor);
            if (replyMovelets.isEmpty()) {
                continue;
            }
            ReplyMovelet lastReplyMovelet = Iterables.getLast(replyMovelets);
            lastReplyMovelet.setLastReplyMovelet(true);
            for (MovilizerReplyMovelet replyMovelet : replyMovelets) {
                try {
                    boolean processed = processReplyMovelet(replyMovelet);
                    if (!processed) {
                        logger.error(">>> cannot process movelet: " + replyMovelet.getMoveletKey());
                    } else {
                        logger.info("Successfully processed reply movelet: " + replyMovelet.getMoveletKey());
                    }
                    success &= processed;

                } catch (CannotProcessReplyMoveletException e) {
                    logger.error(e);
                    return false;
                }
            }
        }

        return success;
    }

    private Map<IReplyMoveletProcessor, List<ReplyMovelet>> getProcessorToReplyMoveletListMap(Set<IReplyMoveletProcessor> replyMoveletProcessors, List<MovilizerReplyMovelet> movilizerReplyMovelets) {
        Map<IReplyMoveletProcessor, List<ReplyMovelet>> map = new HashMap<IReplyMoveletProcessor, List<ReplyMovelet>>();

        for (IReplyMoveletProcessor replyMoveletProcessor : replyMoveletProcessors) {
            List<ReplyMovelet> replyMovelets = new ArrayList<ReplyMovelet>();
            for (MovilizerReplyMovelet movilizerReplyMovelet : movilizerReplyMovelets) {
                if (replyMoveletProcessor.canProcessReplyMovelet(movilizerReplyMovelet)) {
                    replyMovelets.add(new ReplyMovelet(movilizerReplyMovelet));
                }
            }
            if (!replyMovelets.isEmpty()) {
                map.put(replyMoveletProcessor, replyMovelets);
            }
        }
        return map;
    }


    @Override
    public boolean run() {
        logger.debug("Calling movilizer cloud to collect replies using [" + pullCall.getClass().getSimpleName() + "]");

        MovilizerCallResult callResult = pullCall.collectReplies();
        if (!callResult.isSuccess()) {
            logger.error(format("Call to movilizer cloud failed. Error Message: [{0}]", callResult.getError()));
            return false;
        }

        MovilizerResponse response = callResult.getResponse();
        boolean success = invokeResponseObservers(response) &&  processReplyMovelets(response) && processMasterdataAcknowledgements(response);

        if (success) {
            onSuccessfulProcessing(response);
        }
        return success;
    }

    private boolean invokeResponseObservers(MovilizerResponse response) {
        boolean success = true;

        for (IMovilizerResponseObserver responseObserver : responseObservers) {
            try {
                responseObserver.onResponseAvailable(response);
            } catch (KeepItOnTheCloudException e) {
                logger.error(format("Response observer [%s] threw a KeepItOnTheCloudException", responseObserver.getClass().getSimpleName()), e);

                success = false;
            }
        }

        return success;
    }

    private void logResponse(MovilizerResponse movilizerResponse) {
        List<MovilizerReplyMovelet> movelets = movilizerResponse.getReplyMovelet();

        if (movelets.isEmpty()) {
            logger.debug("Received response without reply movelet");
        } else {
            logger.debug("Received Response with reply movelets");
        }

        logger.debug("Acknowledgement key:" + movilizerResponse.getRequestAcknowledgeKey());
        logger.debug("MovilizerStatusMessage:" + movilizerResponse.getStatusMessage().size());
        logger.debug("Reply movelets:" + movilizerResponse.getReplyMovelet().size());
        logger.debug("Masterdata Acknowledgments:" + movilizerResponse.getMasterdataAck().size());
        logger.debug("Movilizer Masterdata Deleted:" + movilizerResponse.getMasterdataDeleted().size());
        logger.debug("Movilizer Masterdata Error:" + movilizerResponse.getMasterdataError().size());
        logger.debug("Movelet Acknowledgments:" + movilizerResponse.getMoveletAck().size());
        logger.debug("Movelets Synced:" + movilizerResponse.getMoveletSynced().size());
        logger.debug("Movelets assignments deleted:" + movilizerResponse.getMoveletAssignmentDeleted().size());
        logger.debug("Participant Acknowledgments:" + movilizerResponse.getParticipantAck().size());
    }

    protected void onSuccessfulProcessing(MovilizerResponse movilizerResponse) {
        logger.debug("All replies processed, acknowledging response with key: " + movilizerResponse.getRequestAcknowledgeKey());
        acknowledgementCall.acknowledgeResponse(movilizerResponse);
        logger.debug("Sending acknowledgment completed: " + movilizerResponse.getRequestAcknowledgeKey());
    }

    protected boolean processReplyMovelet(MovilizerReplyMovelet replyMovelet) throws CannotProcessReplyMoveletException {
        for (IReplyMoveletProcessor replyProcessor : replyProcessors) {
            if (replyProcessor.canProcessReplyMovelet(replyMovelet)) {
                replyProcessor.processReplyMovelet(replyMovelet);
                return true;
            }
        }
        logger.error(format("Cannot process reply movelet: {0} {1}", replyMovelet.getMoveletKey(), replyMovelet.getMoveletKeyExtension()));
        return true;
    }
}
