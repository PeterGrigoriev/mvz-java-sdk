package com.movilizer.util.resource;

import java.io.Reader;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IResourceTextLoader {
    String getResourceAsString(String templateName);

    Reader getResourceAsReader(String templateName);
}
