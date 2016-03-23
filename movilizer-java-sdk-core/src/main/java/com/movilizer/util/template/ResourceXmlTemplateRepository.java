package com.movilizer.util.template;

import com.movilizer.util.velocity.DefaultMoveletTemplateTextLoader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ResourceXmlTemplateRepository extends XmlTemplateRepository {
    public ResourceXmlTemplateRepository(String projectName, Class resourceClass) {
        super(projectName, new DefaultMoveletTemplateTextLoader(resourceClass));
    }

    public ResourceXmlTemplateRepository(String projectName) {
        super(projectName, null);
        setMoveletTemplateTextLoader(new DefaultMoveletTemplateTextLoader(getClass()));
    }
}
