package com.movilizer.push;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.movilitas.movilizer.v12.MovilizerMasterdataDelete;
import com.movilitas.movilizer.v12.MovilizerMasterdataPoolUpdate;
import com.movilitas.movilizer.v12.MovilizerMasterdataReference;
import com.movilitas.movilizer.v12.MovilizerMasterdataUpdate;
import com.movilizer.masterdata.AcknowledgementStatus;
import com.movilizer.masterdata.IMasterdataReaderResult;
import com.movilizer.masterdata.IMasterdataSource;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.movilizer.masterdata.MasterdataReader.REFERENCE_PREFIX;
import static com.movilizer.util.collection.CollectionUtils.toIntegers;
import static com.movilizer.util.string.StringUtils.removePrefix;
import static java.lang.Integer.parseInt;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MasterdataPushRunner {
    private final IMasterdataSource masterdataSource;
    private final IMovilizerConfig config;
    private final IMovilizerPushCall pushCall;

    private static ILogger logger = ComponentLogger.getInstance("Masterdata");

    @Inject
    public MasterdataPushRunner(IMasterdataSource source, IMovilizerConfig config, IMovilizerPushCall pushCall) throws SQLException {
        this.masterdataSource = source;
        this.config = config;
        this.pushCall = pushCall;
    }


    public void run() throws SQLException, XMLStreamException, IOException {
        logger.debug("Started");


        for (IMasterdataXmlSetting setting : config.getMasterdataXmlSettings()) {
            run(setting);
        }


        logger.debug("Finished");
    }

    private void run(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        int loops = setting.getNumberOfLoops();
        if (-1 == loops) {
            loops = Integer.MAX_VALUE;
        }

        for (int i = 0; i < loops; i++) {
            int eventsReceived = runOnce(setting);
            if (eventsReceived == 0) {
                break;
            }
        }
    }

    private int runOnce(IMasterdataXmlSetting setting) throws SQLException, XMLStreamException, IOException {
        logger.debug("Processing masterdata for pool: " + setting.getPool());
        IMasterdataReaderResult result = masterdataSource.read(setting);

        if (result == null) {
            logger.debug("No masterdata to be send for pool: " + setting.getPool());
            return 0;
        }

        List<Integer> eventIds = collectEventIds(result.getMasterdataPoolUpdate());

        pushCall.addMasterdataPoolUpdate(result.getMasterdataPoolUpdate());
        boolean success = pushCall.send();
        pushCall.resetRequest();

        if (success) {
            masterdataSource.acknowledge(setting, eventIds, AcknowledgementStatus.SENT);
            logger.info(MessageFormat.format("Sent {0} master data for pool: [{1}]", eventIds.size(), setting.getPool()));
        } else {
            masterdataSource.acknowledge(setting, eventIds, AcknowledgementStatus.RESEND);
            logger.info(MessageFormat.format("Marking {0} master data for pool: [{1}] to be resent", eventIds.size(), setting.getPool()));
        }

        List<Integer> errorEventIds = toIntegers(result.getErrorEventIds());

        if (!errorEventIds.isEmpty()) {
            masterdataSource.acknowledge(setting, errorEventIds, AcknowledgementStatus.ERROR);
            logger.error(MessageFormat.format("There were {0} master data errors for pool: [{1}]", errorEventIds.size(), setting.getPool()));
        }

        return eventIds.size();
    }

    public static List<Integer> collectEventIds(MovilizerMasterdataPoolUpdate poolUpdate) {
        List<Integer> res = new ArrayList<Integer>();
        for (MovilizerMasterdataDelete delete : poolUpdate.getDelete()) {
            res.add(parseInt(removePrefix(delete.getMasterdataAckKey(), REFERENCE_PREFIX)));
        }
        for (MovilizerMasterdataReference reference : poolUpdate.getReference()) {
            res.add(parseInt(removePrefix(reference.getMasterdataAckKey(), REFERENCE_PREFIX)));
        }
        for (MovilizerMasterdataUpdate update : poolUpdate.getUpdate()) {
            res.add(parseInt(update.getMasterdataAckKey()));
        }
        return res;
    }

}
