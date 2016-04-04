package com.movilizer.util.movelet;

import com.movilizer.moveletbuilder.IMoveletDataProvider;
import com.movilizer.util.datetime.Dates;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.MovilizerConfig;

import java.util.Date;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class SimpleMoveletDataProvider implements IMoveletDataProvider{
    @Override
    public String getMoveletKeyExtension() {
        return "";
    }

    @Override
    public String getNamespace() {
        return getConfig().getMoveletSettings().getNamespace();
    }

    @Override
    public Date getValidTillDate() {
        return Dates.afterNYears(10);
    }

    @Override
    public IMovilizerConfig getConfig() {
        return MovilizerConfig.getInstance(getClass());
    }
}
