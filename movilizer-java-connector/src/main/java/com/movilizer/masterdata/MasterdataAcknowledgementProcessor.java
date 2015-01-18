package com.movilizer.masterdata;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.movilitas.movilizer.v11.MovilizerMasterdataAck;
import com.movilitas.movilizer.v11.MovilizerMasterdataDeleted;
import com.movilitas.movilizer.v11.MovilizerMasterdataError;
import com.movilizer.jaxb.MovilizerJaxbMarshaller;
import com.movilizer.pull.CannotProcessMasterdataAcknowledgementException;
import com.movilizer.pull.CannotProcessMasterdataDeletionException;
import com.movilizer.pull.CannotSubmitMasterdataAcknowledgementsException;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.movilizer.masterdata.MasterdataReader.REFERENCE_PREFIX;
import static com.movilizer.util.string.StringUtils.*;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MasterdataAcknowledgementProcessor implements IMasterdataAcknowledgementProcessor {
    private static class MasterdataAndReferenceSettings {
        private IMasterdataXmlSetting normalSettings;
        private IMasterdataXmlSetting referenceSettings;

        public IMasterdataXmlSetting get(boolean isReference) {
            return isReference ? referenceSettings : normalSettings;
        }

        public void set(IMasterdataXmlSetting settings, boolean isReference) {
            if(isReference) {
                referenceSettings = settings;
            } else {
                normalSettings = settings;
            }
        }
    }

    private final Map<String, MasterdataAndReferenceSettings> settingsByTargetPool;
    private final Set<String> targetPools;
    private final Map<String, IAcknowledgementKeys> acknowledgedKeysBySourcePool;
    private final IMasterdataAcknowledger masterdataAcknowledger;

    private static final ILogger logger = ComponentLogger.getInstance("Masterdata");
    private final MovilizerJaxbMarshaller marshaller = MovilizerJaxbMarshaller.getInstance();

    @Inject
    public MasterdataAcknowledgementProcessor(Set<IMasterdataXmlSetting> masterdataXmlSettings,
                                              IMasterdataAcknowledger masterdataAcknowledger) {
        this.masterdataAcknowledger = masterdataAcknowledger;
        this.settingsByTargetPool = byTargetPool(masterdataXmlSettings);
        this.targetPools = settingsByTargetPool.keySet();
        this.acknowledgedKeysBySourcePool = new HashMap<String, IAcknowledgementKeys>();
    }

    private Map<String, MasterdataAndReferenceSettings> byTargetPool(Collection<IMasterdataXmlSetting> settings) {
        Map<String, MasterdataAndReferenceSettings> map = newHashMap();
        for (IMasterdataXmlSetting setting : settings) {
            boolean isReference = setting.isReference();
            String pool = isReference ? setting.getTargetPool() : setting.getPool();
            MasterdataAndReferenceSettings entry = map.get(pool);
            if(entry == null) {
                entry = new MasterdataAndReferenceSettings();
                map.put(pool, entry);
            } else {
                if (entry.get(isReference) != null) {
                    logger.error("pool is being configured more than once: [" + pool + "] isReference = [" + isReference + "]");
                    continue;
                }
            }
            entry.set(setting, isReference);
        }
        return map;
    }


    @Override
    public Set<String> getTargetPools() {
        return targetPools;
    }

    @Override
    public void processMasterdataAcknowledgement(MovilizerMasterdataAck masterdataAcknowledgement) throws CannotProcessMasterdataAcknowledgementException {
        boolean isReference = isReference(masterdataAcknowledgement);
        IMasterdataXmlSetting setting = getSettings(masterdataAcknowledgement.getPool(), isReference);

        validateMasterdataAcknowledgement(masterdataAcknowledgement, setting);



        processMasterdataAcknowledgement(masterdataAcknowledgement, setting);
    }

    private IMasterdataXmlSetting getSettings(String pool, boolean isReference) {
        MasterdataAndReferenceSettings settings = settingsByTargetPool.get(pool);
        return settings == null ? null : settings.get(isReference);
    }

    // TODO: remove the acknowledgement key prefix when cloud sends correct isReference
    // return masterdataAcknowledgement.isIsReference()
    public static boolean isReference(MovilizerMasterdataAck masterdataAcknowledgement) {
        return isReference(masterdataAcknowledgement.getMasterdataAckKey());
    }


    // TODO: remove the acknowledgement key prefix when cloud sends correct isReference
    // return masterdataDeletion.isIsReference()
    private boolean isReference(MovilizerMasterdataDeleted masterdataDeletion) {
        return isReference(masterdataDeletion.getMasterdataAckKey());
    }

    private static boolean isReference(String masterdataAckKey) {
        return startsWith(masterdataAckKey, REFERENCE_PREFIX);
    }

    @Override
    public void processMasterdataDeletion(MovilizerMasterdataDeleted masterdataDeletion) throws CannotProcessMasterdataDeletionException {
        String pool = masterdataDeletion.getPool();
        String masterdataAckKey = masterdataDeletion.getMasterdataAckKey();
        if(pool != null && masterdataAckKey == null) {
            logger.info("The pool [" + pool + "] has been deleted.");
            return;
        }
        validateMasterdataDeletion(masterdataDeletion);

        boolean isReference = isReference(masterdataDeletion);

        IMasterdataXmlSetting setting = getSettings(pool, isReference);

        if (setting == null) {
            throw new CannotProcessMasterdataDeletionException("Pool is not configured: [" + pool +"] isReference = [" + isReference + "]");
        }

        processMasterdataDeletion(masterdataDeletion, setting);
    }


    private void validateMasterdataDeletion(MovilizerMasterdataDeleted masterdataDeletion) throws CannotProcessMasterdataDeletionException {
        if (masterdataDeletion.getPool() == null) {
            failValidation(masterdataDeletion, "Cannot process masterdata deletion acknowledgement, pool is null. ");
        }

        if(!canParseInt(removePrefix(masterdataDeletion.getMasterdataAckKey(), REFERENCE_PREFIX))) {
            failValidation(masterdataDeletion, "Cannot process masterdata deletion acknowledgement, AckKey is not a number. ");
        }
    }

    private void validateMasterdataAcknowledgement(MovilizerMasterdataAck masterdataAck, IMasterdataXmlSetting setting) throws CannotProcessMasterdataAcknowledgementException {
        String pool = masterdataAck.getPool();
        if (pool == null) {
            failValidation(masterdataAck, "Cannot process masterdata acknowledgement, pool is null. ");
        }

        if(setting == null) {
            failValidation(masterdataAck, "Cannot process masterdata acknowledgement, pool is unknown. " + pool);
        }

        if (masterdataAck.getMasterdataAckKey() == null) {
            failValidation(masterdataAck, "Cannot process masterdata acknowledgement, AckKey is null. ");
        }


        if(!canParseInt(getAcknowledgementKey(masterdataAck, setting))) {
            failValidation(masterdataAck, "Cannot process masterdata acknowledgement, AckKey is not a number. ");
        }
    }

    private String getAcknowledgementKey(MovilizerMasterdataAck masterdataAck, IMasterdataXmlSetting setting) {

        String masterdataAckKey = masterdataAck.getMasterdataAckKey();

        if(setting.isReference()) {
            /// masterdataAck.isIsReference() is false at all times now
            // TODO: remove prefexing after isReference is fixed in the cloud software
            return removePrefix(masterdataAckKey, REFERENCE_PREFIX);
        }
        return masterdataAckKey;
    }

    private void failValidation(MovilizerMasterdataDeleted masterdataDeletion, String errorMessage) throws CannotProcessMasterdataDeletionException {
        String logMessage = errorMessage + " [" + marshaller.marshall(masterdataDeletion) + "] ";
        logger.error(logMessage);

        throw new CannotProcessMasterdataDeletionException(errorMessage);
    }

    private void failValidation(MovilizerMasterdataAck masterdataAck, String errorMessage) throws CannotProcessMasterdataAcknowledgementException {
        String logMessage = errorMessage + " [" + marshaller.marshall(masterdataAck) + "] ";
        logger.error(logMessage);

        throw new CannotProcessMasterdataAcknowledgementException(errorMessage);
    }


    private void processMasterdataAcknowledgement(MovilizerMasterdataAck acknowledgement, IMasterdataXmlSetting setting) {
        String masterdataAckKey = getAcknowledgementKey(acknowledgement, setting);

        String pool = setting.getPool();
        IAcknowledgementKeys keys = acknowledgedKeysBySourcePool.get(pool);
        if (null == keys) {
            keys = new AcknowledgementKeys(setting);
            acknowledgedKeysBySourcePool.put(pool, keys);
        }
        keys.add(parseInt(masterdataAckKey));
    }


    private void processMasterdataDeletion(MovilizerMasterdataDeleted masterdataDeletion, IMasterdataXmlSetting setting) {
        Map<String, IAcknowledgementKeys> keysByPool = acknowledgedKeysBySourcePool;

        processMasterdataDeletion(masterdataDeletion, setting, keysByPool);
    }

    public static void processMasterdataDeletion(MovilizerMasterdataDeleted masterdataDeletion, IMasterdataXmlSetting setting, Map<String, IAcknowledgementKeys> keysByPool) {
        String pool = setting.getPool();
        String masterDataAckKey =  removePrefix(masterdataDeletion.getMasterdataAckKey(), REFERENCE_PREFIX);

        if(masterDataAckKey == null) {
            logger.info("The following pool was deleted from the cloud: [" + pool + "]");
            return;
        }

        IAcknowledgementKeys keys = keysByPool.get(pool);
        if (null == keys) {
            keys = new AcknowledgementKeys(setting);
            keysByPool.put(pool, keys);
        }

        keys.add(parseInt(masterDataAckKey));
    }

    @Override
    public void processMasterdataError(MovilizerMasterdataError masterdataError) throws CannotProcessMasterdataAcknowledgementException {
        String pool = masterdataError.getPool();
        if (pool == null) {
            logger.error("Cannot process masterdata error, pool is not specified " + marshaller.marshall(masterdataError));

            throw new CannotProcessMasterdataAcknowledgementException("No pool specified in masterdata acknowledgement.");
        }


        logger.error(format("Cloud was unable to process masterdata update. Pool = [%s], Group = [%s], Key = [%s], Message = %s",
                masterdataError.getPool(),
                masterdataError.getGroup(),
                masterdataError.getKey(),
                masterdataError.getMessage()));
    }

    @Override
    public void submit() throws CannotSubmitMasterdataAcknowledgementsException {
        try {
            Set<String> pools = acknowledgedKeysBySourcePool.keySet();

            for (String pool : pools) {
                submit(acknowledgedKeysBySourcePool.get(pool));
            }
        } catch (SQLException e) {
            throw new CannotSubmitMasterdataAcknowledgementsException(e);
        }
    }

    private void submit(IAcknowledgementKeys keys) throws SQLException {
        if(keys.size() == 0) {
            return;
        }
        IMasterdataXmlSetting setting = keys.getSetting();
        logger.info("Acknowledging [" + keys.size() + "] master data entries for pool [" + setting.getPool() + "] with status [OK (received by the cloud)]");
        Collection<Integer> eventIds = newHashSet(keys.getKeys());
        if(eventIds.size() != keys.size()) {
            logger.warn("Some of the master data acknowledgements were received twice! Total: [" + keys.size() + "], unique: [" + eventIds.size() + "]");
        }

        try {
            masterdataAcknowledger.acknowledge(setting, eventIds, AcknowledgementStatus.OK);
        }
        catch (SQLException t) {
            logger.error("Could not acknowledge the following event IDs with status OK");
            for (Integer key : eventIds) {
                logger.warn("Could not acknowledge the following event with status OK: [" + String.valueOf(key) + "]");
            }
            throw t;
        }
    }
}
