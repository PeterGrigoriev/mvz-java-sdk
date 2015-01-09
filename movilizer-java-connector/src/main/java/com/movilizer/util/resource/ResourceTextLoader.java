package com.movilizer.util.resource;

import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ResourceTextLoader implements IResourceTextLoader {
    protected final Class classWithResources;

    protected ResourceTextLoader(Class classWithResources) {
        this.classWithResources = classWithResources;
    }

    public static IResourceTextLoader forClass(Class classWithResources) {
        return new ResourceTextLoader(classWithResources);
    }

    private static final ILogger logger = ComponentLogger.getInstance("ResourceTextLoader");

    @Override
    public String getResourceAsString(String templateName) {
        InputStream stream = classWithResources.getResourceAsStream(templateName);
        if (null == stream) {
            logger.warn("cannot find resource: " + templateName);
            return null;
        }
        try {
            return IOUtils.toString(stream, "UTF-8");
        } catch (IOException e) {
            logger.fatal(e);
            return null;
        }
    }

    @Override
    public Reader getResourceAsReader(String templateName) {
        String templateAsString = getResourceAsString(templateName);
        return templateAsString == null ? null : new StringReader(templateAsString);
    }
}
