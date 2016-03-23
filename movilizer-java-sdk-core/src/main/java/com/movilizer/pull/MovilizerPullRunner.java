package com.movilizer.pull;


import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.movilitas.movilizer.v14.*;
import com.movilizer.acknowledgement.IMovilizerAcknowledgementCall;
import com.movilizer.connector.MovilizerCallResult;
import com.movilizer.masterdata.IMasterdataAcknowledgementProcessor;
import com.movilizer.masterdata.MasterDataResponseObserver;
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
    protected final Set<IMovilizerResponseObserver> responseObservers;

    protected final IMovilizerResponseObserver masterDataResponseObserver;


    @Inject
    public MovilizerPullRunner(IMovilizerPullCall pullCall, IMovilizerAcknowledgementCall acknowledgementCall,
                               Set<IReplyMoveletProcessor> replyProcessors,
                               Set<IMovilizerResponseObserver> responseObservers,
                               IMasterdataAcknowledgementProcessor masterdataAcknowledgementProcessor) {
        this.pullCall = pullCall;
        this.acknowledgementCall = acknowledgementCall;
        this.replyProcessors = nullToEmptySet(replyProcessors);
        this.masterDataResponseObserver = new MasterDataResponseObserver(masterdataAcknowledgementProcessor);
        this.responseObservers = nullToEmptySet(responseObservers);
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

    private boolean processMasterdataAcknowledgements(MovilizerResponse response) {
        try {
            masterDataResponseObserver.onResponseAvailable(response);
            return true;
        } catch (KeepItOnTheCloudException e) {
            return false;
        }
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
