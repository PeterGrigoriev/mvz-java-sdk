package com.movilizer.util.template;

import com.movilizer.util.MovilizerTemplateUtils;
import com.movilizer.util.velocity.VelocityRunner;
import org.apache.velocity.VelocityContext;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class XmlTemplate implements IXmlTemplate {
    private final VelocityRunner velocityRunner;

    XmlTemplate(VelocityRunner velocityRunner) {
        this.velocityRunner = velocityRunner;
    }

    public static final MovilizerTemplateUtils UTILS = new MovilizerTemplateUtils();


    private static final String[] MEL_PREFIXES = {"local", "global", "answer", "question", "masterdata", "document", "base64", "customizing"};

    @Override
    public String produceXml(Object dataProvider) {
        VelocityRunner runner = getRunner();
        VelocityContext context = runner.getContext();

        context.put("context", dataProvider);
        context.put("utils", UTILS);

        for (String melPrefix : MEL_PREFIXES) {
            context.put(melPrefix, "$" + melPrefix);
        }

        String res = runner.merge();

        // next line will allow GC on objects in context.
        // otherwise everything we put there would stay
        // till next time buildText() is called on
        // the same provider
        context.put("context", null);

        return res;
    }

    public VelocityRunner getRunner() {
        return velocityRunner;
    }

    @Override
    public String toString() {
        return "XmlTemplate{" +
                "velocityRunner=" + velocityRunner.toString() +
                '}';
    }
}
