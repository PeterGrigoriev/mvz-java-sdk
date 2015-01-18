package com.movilizer.push;

import com.google.inject.ImplementedBy;
import com.movilitas.movilizer.v11.MovilizerRequest;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(TemplateRequestPush.class)
public interface ITemplateRequestPush {
    void push(String templateName, Object context) throws Exception;

    MovilizerRequest createRequest(String templateName, Object context) throws Exception;
}
