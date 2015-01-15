package com.movilizer.util.config;

import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.masterdata.IMasterdataXmlSetting;

import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMovilizerConfig extends IConfiguration {

    IMovilizerCloudSystem getMovilizerSystem();

    IMovilizerCloudSystem getMasterDataSystem();

    IProxyInfo getProxyInfo();

    IKeyStoreInfo getKeyStoreInfo();

    IJdbcSettings getJdbcSettings();

    IMoveletSettings getMoveletSettings();

    IMovilizerPullSettings getPullSettings();

    IMovilizerPushSettings getPushSettings();

    Collection<IMasterdataXmlSetting> getMasterdataXmlSettings();

}
