package com.movilizer.util.velocity;

import com.movilizer.util.resource.ResourceTextLoader;
import com.movilizer.util.template.IXmlTemplateTextLoader;

import java.io.Reader;
import java.io.StringReader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class DefaultMoveletTemplateTextLoader extends ResourceTextLoader implements IXmlTemplateTextLoader {

    public DefaultMoveletTemplateTextLoader() {
        super(DefaultMoveletTemplateTextLoader.class);
    }

    public DefaultMoveletTemplateTextLoader(Class classWithResources) {
        super(classWithResources);
    }

    @Override
    public Reader getTemplateText(String templateName) {
        if ("VM_global_library.vm".equals(templateName)) {
            return null;
        }
        String templateAsString = getResourceAsString(templateName);
        return templateAsString == null ? null : new StringReader(templateAsString);
    }

}
