package com.movilizer.util.template;

import com.movilitas.movilizer.v11.MovilizerMovelet;
import com.movilizer.moveletbuilder.*;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.ArrayList;
import java.util.List;

import static com.movilizer.util.datetime.ValidityDateUtils.setValidTillDate;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class TemplateMoveletBuilder implements IMoveletBuilder {
    protected final IMoveletDataProvider moveletDataProvider;
    protected final String[] templateNames;
    protected final ITemplateRepository repository;
    protected final List<IXmlTemplate> moveletTemplates;

    public TemplateMoveletBuilder(IMoveletDataProvider moveletDataProvider, ITemplateRepository repository, String... templateNames) {
        this.moveletDataProvider = moveletDataProvider;
        this.templateNames = templateNames;
        this.repository = repository;
        moveletTemplates = getTemplates(repository, this.templateNames);
    }


    private static final ILogger logger = ComponentLogger.getInstance("TemplateMoveletBuilder");

    private List<IXmlTemplate> getTemplates(ITemplateRepository repository, String[] templateNames) {
        List<IXmlTemplate> templateList = new ArrayList<IXmlTemplate>();
        for (String templateName : templateNames) {
            IXmlTemplate template = getTemplate(repository, templateName);
            if (template == null) {
                logger.error("Cannot load template: " + templateName);
                return new ArrayList<IXmlTemplate>();
            }
            templateList.add(template);
        }
        return templateList;
    }


    private static IXmlTemplate getTemplate(ITemplateRepository repository, String templateName) {
        try {
            return repository.getTemplate(templateName);
        } catch (Exception e) {
            logger.error("Cannot get template [" + templateName + "] using the repository [" + repository + "]", e);
            return null;
        }
    }

    @Override
    public List<MovilizerMovelet> buildMovelets() {
        List<MovilizerMovelet> movelets = new ArrayList<MovilizerMovelet>();
        for (IXmlTemplate moveletTemplate : moveletTemplates) {
            String moveletXml = moveletTemplate.produceXml(moveletDataProvider);
            XmlMoveletBuilder xmlMoveletBuilder = new XmlMoveletBuilder(moveletXml);
            movelets.addAll(xmlMoveletBuilder.buildMovelets());
        }

        for (MovilizerMovelet movelet : movelets) {
            // movelet.setMoveletKeyExtension(moveletDataProvider.getMoveletKeyExtension());
            logger.trace(String.format("Setting validity date: %s|%s->%s", movelet.getMoveletKey(), movelet.getMoveletKeyExtension(), moveletDataProvider.getValidTillDate()));
            setValidTillDate(movelet, moveletDataProvider.getValidTillDate());
        }

        return movelets;
    }

    @Override
    public void onRequestAction(RequestEvent action) {
        if(moveletDataProvider instanceof IMoveletPushListener) {
            ((IMoveletPushListener)moveletDataProvider).onRequestAction(action);
        }
    }
}
