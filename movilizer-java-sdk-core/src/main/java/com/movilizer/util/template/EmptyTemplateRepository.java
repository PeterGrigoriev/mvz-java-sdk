package com.movilizer.util.template;

import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

/**
 * @author Peter.Grigoriev@movilizer.com
 */

public class EmptyTemplateRepository implements ITemplateRepository {
    private static ILogger logger = ComponentLogger.getInstance("MovilizerUtils");

    @Override
    public IXmlTemplate getTemplate(String templateName) throws Exception {
        logger.warn("Trying to get a resource with EmptyTemplateRepository. Please override MovilizerModule.setUp method to bind your template repository.");

        return null;
    }
}
