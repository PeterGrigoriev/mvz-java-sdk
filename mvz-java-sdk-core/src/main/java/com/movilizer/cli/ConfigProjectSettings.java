package com.movilizer.cli;

import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.projectmanagement.IMobileProjectSettings;
import com.movilizer.util.config.IMovilizerConfig;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ConfigProjectSettings implements IMobileProjectSettings {

    private final IMobileProjectDescription projectDescription;
    private final IMovilizerConfig config;

    public ConfigProjectSettings(IMobileProjectDescription projectDescription, IMovilizerConfig config) {
        this.projectDescription = projectDescription;
        this.config = config;
    }

    @Override
    public IMovilizerCloudSystem getMoveletCloudSystem() {
        return config.getMovilizerSystem();
    }

    @Override
    public IMovilizerCloudSystem getMasterDataCloudSystem() {
        return config.getMasterDataSystem();
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return projectDescription.getName();
    }

    @Override
    public int getVersion() {
        return projectDescription.getVersion();
    }
}
