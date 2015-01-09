package com.movilizer.moveletbuilder;

import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.MovilizerConfig;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public abstract class MoveletDataProviderBase implements IMoveletDataProvider {
    @Override
    public IMovilizerConfig getConfig() {
        return MovilizerConfig.getInstance(getClass());
    }
}
