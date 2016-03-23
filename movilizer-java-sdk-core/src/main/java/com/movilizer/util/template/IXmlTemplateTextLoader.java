package com.movilizer.util.template;

import java.io.Reader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IXmlTemplateTextLoader {
    Reader getTemplateText(String templateName);
}
