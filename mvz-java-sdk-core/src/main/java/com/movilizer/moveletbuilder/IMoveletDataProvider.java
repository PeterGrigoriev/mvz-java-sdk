package com.movilizer.moveletbuilder;

import com.movilizer.util.config.IMovilizerConfig;

import java.util.Date;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMoveletDataProvider {
    String getMoveletKeyExtension();

    String getNamespace();

    Date getValidTillDate();

    IMovilizerConfig getConfig();
}