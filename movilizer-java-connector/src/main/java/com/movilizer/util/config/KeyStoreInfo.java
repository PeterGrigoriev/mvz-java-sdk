package com.movilizer.util.config;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class KeyStoreInfo implements IKeyStoreInfo {
    private final String location;
    private final String password;

    public KeyStoreInfo(String location, String password) {
        this.location = location;
        this.password = password;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
