package com.movilizer.masterdata;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.movilitas.movilizer.v12.MovilizerMasterdataPoolUpdate;
import com.movilizer.jaxb.MovilizerJaxbUnmarshaller;
import com.movilizer.util.template.ITemplateRepository;
import com.movilizer.util.template.IXmlTemplate;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MasterdataUpdateBuilder implements IMasterdataUpdateBuilder {
    private final ITemplateRepository templateRepository;

    @Inject
    public MasterdataUpdateBuilder(@Named("Masterdata") ITemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    private static final ILogger logger = ComponentLogger.getInstance("Masterdata");


    @Override
    public MovilizerMasterdataPoolUpdate build(IMasterdataUpdateInfoProvider provider, String templateName) throws Exception {
        IXmlTemplate template = templateRepository.getTemplate(templateName);
        String xml = template.produceXml(provider);
        try {
            return MovilizerJaxbUnmarshaller.getInstance().unmarshall(MovilizerMasterdataPoolUpdate.class, xml);
        } catch (Exception e) {
            logger.error("Cannot de-serialize MasterdataUpdate XML: " + xml);
            throw e;
        }
    }
}
