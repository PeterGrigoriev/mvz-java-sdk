package com.movilizer.projectmanagement;

import com.movilizer.connector.IMovilizerCloudSystem;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMobileProjectSettings extends IMobileProjectDescription {

    IMovilizerCloudSystem getMoveletCloudSystem();

    IMovilizerCloudSystem getMasterDataCloudSystem();

    int getId();
}