package com.movilizer.util.template;

import com.movilizer.util.velocity.VelocityRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class XmlTemplateRepository implements ITemplateRepository {
    private final String name;
    private IXmlTemplateTextLoader moveletTemplateTextLoader;
    private final Map<String, IXmlTemplate> templates;


    public XmlTemplateRepository(String projectName,
                                 IXmlTemplateTextLoader moveletTemplateTextLoader) {
        this.name = projectName;
        this.moveletTemplateTextLoader = moveletTemplateTextLoader;
        templates = new HashMap<String, IXmlTemplate>();
    }

    protected void setMoveletTemplateTextLoader(IXmlTemplateTextLoader moveletTemplateTextLoader) {
        this.moveletTemplateTextLoader = moveletTemplateTextLoader;
    }

    @Override
    public IXmlTemplate getTemplate(String templateName) throws Exception {
        IXmlTemplate template = templates.get(templateName);
        if (template == null) {
            template = new XmlTemplate(VelocityRunner.newInstance(moveletTemplateTextLoader, templateName));
            templates.put(templateName, template);
        }
        return template;
    }

    public String getName() {
        return name;
    }
}
