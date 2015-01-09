package com.movilizer.util.velocity;

import com.movilizer.util.template.IXmlTemplateTextLoader;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.tools.ant.util.ReaderInputStream;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import java.io.InputStream;
import java.io.Reader;

/**
 * Author: Peter.Grigoriev@movilizer.com
 */
public class VelocityResourceLoader extends ResourceLoader {

    private final IXmlTemplateTextLoader moveletTemplateLoader;

    public VelocityResourceLoader(IXmlTemplateTextLoader moveletTemplateLoader) {
        this.moveletTemplateLoader = moveletTemplateLoader;
    }


    @Override
    public void init(ExtendedProperties extendedProperties) {
    }

    @Override
    public InputStream getResourceStream(String templateName) throws ResourceNotFoundException {
        Reader templateText = moveletTemplateLoader.getTemplateText(templateName);
        return null == templateText ? null : new ReaderInputStream(templateText, "UTF-8");
    }

    @Override
    public boolean isSourceModified(Resource resource) {
        return false;
    }

    @Override
    public long getLastModified(Resource resource) {
        return 0;
    }
}
