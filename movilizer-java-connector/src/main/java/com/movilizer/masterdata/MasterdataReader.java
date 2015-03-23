package com.movilizer.masterdata;

import com.movilitas.movilizer.v12.*;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static com.movilizer.util.string.StringUtils.isNullOrEmpty;

/**
 * @author Peter.Grigoriev@movilizer.com.
 */
public abstract class MasterdataReader implements IMasterdataReader {
    public static final String REFERENCE_PREFIX = "REF_";
    private final ILogger logger = ComponentLogger.getInstance("MasterdataXmlReader");

    public static String getObjectKey(IMasterdataChange masterdataChange) {
        return masterdataChange == null ? null : masterdataChange.getObjectKey();
    }

    public IMasterdataChange convertToMasterdataChange(IParsedMasterdataEvent element, IMasterdataXmlSetting settings) throws IllegalMasterdataFormatException {

        if(settings.isReference()) {
            return convertToReferenceOrDeference(element);
        }
        return convertToUpdateOrDelete(element);

    }

    public static IMasterdataChange convertToReferenceOrDeference(IParsedMasterdataEvent element) throws IllegalMasterdataFormatException {
        MasterdataEventType type = element.getEventType();
        return type == MasterdataEventType.DELETE ?
                new MasterdataChange(toMasterdataDereference(element)) :
                new MasterdataChange(toMasterdataReference(element));
    }

    public static MovilizerMasterdataReference toMasterdataReference(IParsedMasterdataEvent element) throws IllegalMasterdataFormatException {
        MovilizerMasterdataReference reference = new MovilizerMasterdataReference();
        reference.setGroup(element.getGroup());
        reference.setKey(element.getObjectId());
        reference.setMasterdataAckKey(toReferenceAckKey(element.getEventId()));
        return reference;
    }

    public static MovilizerMasterdataDelete toMasterdataDereference(IParsedMasterdataEvent element) throws IllegalMasterdataFormatException {
        MovilizerMasterdataDelete delete = new MovilizerMasterdataDelete();
        delete.setGroup(element.getGroup());
        delete.setMasterdataAckKey(toReferenceAckKey(element.getEventId()));
        delete.setKey(element.getObjectId());
        return delete;
    }


    private static String toReferenceAckKey(String eventId) {
        return REFERENCE_PREFIX + eventId;
    }

    private IMasterdataChange convertToUpdateOrDelete(IParsedMasterdataEvent element) throws IllegalMasterdataFormatException {
        MasterdataEventType type = element.getEventType();
        return type == MasterdataEventType.DELETE ?
                new MasterdataChange(toMasterdataDelete(element)) :
                new MasterdataChange(toMasterdataUpdate(element));
    }

    public MovilizerMasterdataUpdate toMasterdataUpdate(IParsedMasterdataEvent parsedMasterdataEvent) throws IllegalMasterdataFormatException {
        MovilizerMasterdataUpdate update = new MovilizerMasterdataUpdate();

        update.setGroup(parsedMasterdataEvent.getGroup());
        update.setKey(parsedMasterdataEvent.getObjectId());
        update.setMasterdataAckKey(parsedMasterdataEvent.getEventId());
        update.setDescription(parsedMasterdataEvent.getDescription());
        update.setFilter1(parsedMasterdataEvent.getFilter1());
        update.setFilter2(parsedMasterdataEvent.getFilter2());
        update.setFilter3(parsedMasterdataEvent.getFilter3());
        update.setFilter4(parsedMasterdataEvent.getFilter4());
        update.setFilter5(parsedMasterdataEvent.getFilter5());
        update.setFilter6(parsedMasterdataEvent.getFilter6());

        MovilizerGenericDataContainer container = new MovilizerGenericDataContainer();
        update.setData(container);
        List<MovilizerGenericDataContainerEntry> movilizerGenericDataContainerEntry = container.getEntry();

        Set<String> properFieldNames = parsedMasterdataEvent.getProperFieldNames();
        for (String fieldName : properFieldNames) {
            movilizerGenericDataContainerEntry.add(toContainerEntry(fieldName, parsedMasterdataEvent.getField(fieldName)));
        }
        return update;
    }

    public MovilizerMasterdataDelete toMasterdataDelete(IParsedMasterdataEvent element) throws IllegalMasterdataFormatException {
        MovilizerMasterdataDelete delete = new MovilizerMasterdataDelete();
        delete.setKey(element.getObjectId());
        delete.setMasterdataAckKey(element.getEventId());
        return delete;
    }

    private MovilizerGenericDataContainerEntry toContainerEntry(String name, String value) {
        MovilizerGenericDataContainerEntry entry = new MovilizerGenericDataContainerEntry();

        entry.setValstr(value);
        entry.setName(name);

        return entry;
    }

    public abstract List<IParsedMasterdataEvent> parse(Reader reader, IMasterdataXmlSetting settings) throws XMLStreamException, IOException;

    @Override
    public IMasterdataReaderResult read(Reader reader, IMasterdataXmlSetting settings) throws IOException, XMLStreamException {
        List<IParsedMasterdataEvent> elements = parse(reader, settings);

        // maps object id to the corresponding update or delete
        List<IMasterdataChange> changes = newArrayList();
        List<String> errorEventIds = new ArrayList<String>();

        for (IParsedMasterdataEvent parsedMasterdataEvent : elements) {
            try {
                changes.add(convertToMasterdataChange(parsedMasterdataEvent, settings));
            } catch (IllegalMasterdataFormatException e) {
                logger.error(e);
                String eventId;
                try {
                    eventId = parsedMasterdataEvent.getEventId();
                    if (!isNullOrEmpty(eventId)) {
                        errorEventIds.add(eventId);
                    }
                } catch (IllegalMasterdataFormatException e1) {
                    // here we can not acknowledge
                }
            }
        }
        if(isOnlyLastChangePerObjectNeeded(settings)) {
            changes = compactChanges(changes);
        }
        String pool = settings.isReference() ? settings.getTargetPool() : settings.getPool();
        return new MasterdataReaderResult(pool, changes, errorEventIds);
    }

    private static List<IMasterdataChange> compactChanges(Iterable<IMasterdataChange> changes) {
        LinkedHashMap<String, IMasterdataChange> changeMap = newLinkedHashMap();
        for (IMasterdataChange change : changes) {
            changeMap.put(getObjectKey(change), change);
        }
        return newArrayList(changeMap.values());
    }

    private static boolean isOnlyLastChangePerObjectNeeded(IMasterdataXmlSetting settings) {
        return !settings.isReference();
    }
}
