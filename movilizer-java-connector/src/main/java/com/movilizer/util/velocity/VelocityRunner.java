package com.movilizer.util.velocity;

import com.movilizer.util.template.IXmlTemplateTextLoader;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.LogChute;

import java.io.StringWriter;

import static java.text.MessageFormat.format;

/**
 * Author: Peter.Grigoriev@movilizer.com
 */
public class VelocityRunner {
    private final VelocityContext context;
    private final org.apache.velocity.Template template;



    public static VelocityRunner newInstance(IXmlTemplateTextLoader textLoader, String templateName) throws Exception {
        return new VelocityRunner(textLoader, templateName);
    }

    private static ILogger logger = ComponentLogger.getInstance("TemplateEngine");

    private static void trace(String format, Object... args) {
        String message = format(format, args);
        logger.trace(message);
    }

    private VelocityRunner(IXmlTemplateTextLoader textLoader, String template) throws Exception {
        VelocityLogger velocityLogger = new VelocityLogger();
        VelocityEngine engine = initVelocity(textLoader, velocityLogger);
        context = new VelocityContext();

        this.template = engine.getTemplate(template, "UTF-8");

        trace("VelocityRunner created for template {0}", template);
    }


    private VelocityEngine initVelocity(IXmlTemplateTextLoader textLoader, LogChute logger) throws Exception {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM, logger);


        engine.setProperty("resource.loader", "file");
        engine.setProperty("file.resource.loader.instance",
                new VelocityResourceLoader(textLoader));
        engine.setProperty("file.resource.loader.path", "LoaderPath");


        engine.init();

        return engine;
    }

    public void put(String name, Object value) {
        context.put(name, value);
    }


    public VelocityContext getContext() {
        return context;
    }

    public String merge() {
        StringWriter stringWriter = new StringWriter();

        try {
            template.merge(context, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            logger.error("Cannot merge velocity template", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return "VelocityRunner{" +
                "template=" + template +
                '}';
    }
}
