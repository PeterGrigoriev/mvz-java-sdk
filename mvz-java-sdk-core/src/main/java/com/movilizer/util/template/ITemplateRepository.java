package com.movilizer.util.template;

import com.google.inject.ImplementedBy;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(EmptyTemplateRepository.class)
public interface ITemplateRepository {
    IXmlTemplate getTemplate(String templateName) throws Exception;
}
