package com.movilizer.masterdata;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.movilitas.movilizer.v12.MovilizerMasterdataPoolUpdate;
import com.movilitas.movilizer.v12.MovilizerResponse;
import com.movilizer.acknowledgement.MovilizerAcknowledgmentCall;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IMovilizerRequestSender;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.connector.MovilizerCallResult;
import com.movilizer.pull.KeepItOnTheCloudException;
import com.movilizer.push.MovilizerSynchronousPushCall;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.movilizer.util.collection.CollectionUtils.toIntegers;
import static java.text.MessageFormat.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MasterDataConnector implements IMasterDataConnector {

    private final static ILogger logger = ComponentLogger.getInstance(MasterDataConnector.class);

    private final IMasterdataSource masterdataSource;
    private final IMovilizerCloudSystem masterdataCloudSystem;

    private final IProxyInfo proxyInfo;
    private final Set<IMasterdataXmlSetting> masterdataSettings;

    private final IMovilizerRequestSender requestSender;

    @Inject
    public MasterDataConnector(IMasterdataSource masterdataSource, IMovilizerCloudSystem masterdataCloudSystem, IProxyInfo proxyInfo, Set<IMasterdataXmlSetting> masterdataSettings, IMovilizerRequestSender requestSender) {
        this.masterdataSource = masterdataSource;
        this.masterdataCloudSystem = masterdataCloudSystem;
        this.proxyInfo = proxyInfo;
        this.masterdataSettings = masterdataSettings;
        this.requestSender = requestSender;
    }

    @Override
    public void run() {
        for (IMasterdataXmlSetting setting : masterdataSettings) {
            run(setting);
        }
    }

    public void run(IMasterdataXmlSetting setting) {
        int numberOfLoops = setting.getNumberOfLoops();
        for (int i = 0; i < numberOfLoops; i++) {
            try {
                boolean anyUpdatesSent = run(setting, createSynchronousCall());
                if (!anyUpdatesSent) {
                    break;
                }
            } catch (Exception e) {
                logger.error("Failed to run for pool [" + setting.getPool() + "]", e);
            }
        }
    }

    private MovilizerSynchronousPushCall createSynchronousCall() {
        return new MovilizerSynchronousPushCall(masterdataCloudSystem, proxyInfo, requestSender, null, null);
    }

    private boolean run(IMasterdataXmlSetting setting, MovilizerSynchronousPushCall call) throws SQLException, IOException, XMLStreamException {
        String pool = setting.getPool();
        logger.debug(format("Running for pool [{0}]", pool));
        IMasterdataReaderResult result = masterdataSource.read(setting);
        if(null == result) {
            logger.debug(format("No master data updates for pool [{0}]", pool));
            return false;
        }
        handleReadErrors(setting, result);
        MovilizerMasterdataPoolUpdate masterdataPoolUpdate = result.getMasterdataPoolUpdate();
        call.addMasterdataPoolUpdate(masterdataPoolUpdate);
        MovilizerCallResult callResult = call.synchronousSend();

        if(!callResult.isSuccess()) {
            logger.error("Movilizer cloud call failed with error [" + callResult.getError() + "]");
            return false;
        }

        IMasterdataAcknowledgementProcessor acknowledgementProcessor = new MasterdataAcknowledgementProcessor(masterdataSettings, masterdataSource);
        MasterDataResponseObserver responseObserver = new MasterDataResponseObserver(acknowledgementProcessor);

        MovilizerResponse response = callResult.getResponse();
        try {
            responseObserver.onResponseAvailable(response);
        } catch (KeepItOnTheCloudException e) {
            logger.error("Could not process cloud response. The response package will not be acknowledged.");
            return false;
        }
        acknowledge(response);
        return true;
    }

    private void acknowledge(MovilizerResponse response) {
        MovilizerAcknowledgmentCall acknowledgmentCall = new MovilizerAcknowledgmentCall(masterdataCloudSystem, proxyInfo, requestSender);
        acknowledgmentCall.acknowledgeResponse(response);
    }

    private void handleReadErrors(IMasterdataXmlSetting setting, IMasterdataReaderResult result) throws SQLException {
        List<String> eventIds = result.getErrorEventIds();
        if(null == eventIds || eventIds.isEmpty()) {
            return;
        }

        List<Integer> errorEventIds = parseEventIds(eventIds);

        if (null == errorEventIds || errorEventIds.isEmpty()) {
            return;
        }

        logger.warn("Acknowledging " + errorEventIds.size() + " master data entries as ERROR");
        masterdataSource.acknowledge(setting, errorEventIds, AcknowledgementStatus.ERROR);
        logger.debug("Done acknowledging " + errorEventIds.size() + " master data entries as ERROR");
    }

    private static List<Integer> parseEventIds(List<String> eventIds) {
        List<String> failedToConvertIds = newArrayList();
        List<Integer> parsedEventIds = toIntegers(eventIds, failedToConvertIds);
        for (String failedToConvertId : failedToConvertIds) {
            logger.error("Master Data Event id cannot be converted into an integer [" + failedToConvertId + "]");
        }
        return parsedEventIds;
    }
}
