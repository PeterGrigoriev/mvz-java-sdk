package com.movilizer.util.proxy;

import com.movilizer.connector.IProxyInfo;

import java.util.Properties;

import static com.movilizer.util.string.StringUtils.isNullOrEmpty;


/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ProxyUtil {
    public static void applyProxy(IProxyInfo proxyInfo) {
        Properties properties = System.getProperties();

        if (null == proxyInfo || isNullOrEmpty(proxyInfo.getProxyHost())) {
            properties.put("proxySet", false);
            return;
        }

        properties.put("proxySet", true);
        properties.put("http.proxyHost", proxyInfo.getProxyHost());
        properties.put("http.proxyPort", proxyInfo.getProxyPort());
        properties.put("https.proxyHost", proxyInfo.getProxyHost());
        properties.put("https.proxyPort", proxyInfo.getProxyPort());
    }
}
