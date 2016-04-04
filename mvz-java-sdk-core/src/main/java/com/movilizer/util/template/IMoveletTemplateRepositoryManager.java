package com.movilizer.util.template;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMoveletTemplateRepositoryManager {
    ITemplateRepository get(String name, IXmlTemplateTextLoader moveletTemplateTextLoader);
}
