package com.movilizer.util.config;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IConfiguration {
    int getInt(String setting);

    String getString(String setting);
}
