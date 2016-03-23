package com.movilizer.masterdata;

import com.google.inject.Singleton;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MasterdataXmlReader extends MasterdataReader implements IMasterdataXmlReader {


    private final XMLInputFactory factory = XMLInputFactory.newInstance();


    @Override
    public List<IParsedMasterdataEvent> parse(Reader reader, IMasterdataXmlSetting settings) throws XMLStreamException {
        XMLStreamReader streamReader = factory.createXMLStreamReader(reader);
        int level = 0;
        Map<String, String> currentElement = null;
        List<IParsedMasterdataEvent> result = new ArrayList<IParsedMasterdataEvent>();

        IMasterdataFieldNames fieldNames = settings.getFieldNames();


        while (streamReader.hasNext()) {
            int eventType = streamReader.next();

            switch (eventType) {
                case XMLEvent.START_DOCUMENT:
                    continue;
                case XMLEvent.END_DOCUMENT:
                    break;
                case XMLEvent.START_ELEMENT:
                    level++;
                    String name = streamReader.getName().toString();
                    if (level == 2) {
                        currentElement = new HashMap<String, String>();
                    }
                    if (level == 3) {
                        String elementText = streamReader.getElementText();
                        assert currentElement != null;
                        currentElement.put(name, elementText);


                        level--;
                    }

                    break;

                case XMLEvent.END_ELEMENT:
                    if (level == 2) {
                        result.add(new ParsedMasterdataEvent(currentElement, fieldNames));
                    }
                    level--;
            }
        }

        return result;
    }
}
