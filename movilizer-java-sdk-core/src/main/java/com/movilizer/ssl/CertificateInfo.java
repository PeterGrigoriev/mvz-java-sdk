package com.movilizer.ssl;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CertificateInfo implements ICertificateInfo {
    private final String alias;
    private final String location;

    public CertificateInfo(String location, String alias) {
        this.alias = alias;
        this.location = location;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getLocation() {
        return location;
    }
}
