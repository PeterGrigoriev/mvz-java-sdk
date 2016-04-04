package com.movilizer.util.proxy;


import com.movilizer.connector.IProxyInfo;

public class ProxyInfo implements IProxyInfo {
    private final String proxyHost;
    private final String proxyPort;

    public ProxyInfo(String proxyHost, int proxyPort) {
        this(proxyHost, String.valueOf(proxyPort));
    }

    public ProxyInfo(String proxyHost, String proxyPort) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    @Override
    public String getProxyHost() {
        return proxyHost;
    }

    @Override
    public String getProxyPort() {
        return proxyPort;
    }
}
