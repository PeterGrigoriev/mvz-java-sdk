package com.movilizer.util.config;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class JdbcSettings implements IJdbcSettings {
    private final String url;
    private final String user;
    private final String password;

    public JdbcSettings(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
