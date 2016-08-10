package com.movilizer.mockdata.saved;

import com.movilitas.movilizer.v15.MovilizerReplyMovelet;
import com.movilizer.jaxb.MovilizerJaxbUnmarshaller;
import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class SavedMovilizerObjectLoader {
    private static SavedMovilizerObjectLoader instance = new SavedMovilizerObjectLoader();

    public static SavedMovilizerObjectLoader getInstance() {
        return instance;
    }

    public String loadXml(String resourceName) throws IOException {
        InputStream stream = SavedMovilizerObjectLoader.class.getResourceAsStream(resourceName);
        if (null == stream) {
            throw new IOException("Resource not found: " + resourceName);
        }
        return IOUtils.toString(stream, "UTF-8");
    }

    public <T> T load(Class<T> klass, String resourceName) throws IOException, ParserConfigurationException, SAXException, JAXBException {
        String xml = loadXml(resourceName);
        return MovilizerJaxbUnmarshaller.getInstance().unmarshall(klass, xml);
    }

    public MovilizerReplyMovelet loadMovilizerReplyMovelet(String resourceName) throws ParserConfigurationException, JAXBException, SAXException, IOException {
        return load(MovilizerReplyMovelet.class, resourceName);
    }
}
