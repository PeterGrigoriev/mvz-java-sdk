package com.movilizer;

import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.connector.MovilizerCloudSystem;
import com.movilizer.util.proxy.ProxyInfo;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class TestConstants {
    public static final IMovilizerCloudSystem TEST_CLOUD_SYSTEM = new MovilizerCloudSystem(
            1234,
            "password",
            "http://movilizer.com",
            180000
    );

    public static IProxyInfo TEST_PROXY = new ProxyInfo("proxy.sample.com", 8080);

}
