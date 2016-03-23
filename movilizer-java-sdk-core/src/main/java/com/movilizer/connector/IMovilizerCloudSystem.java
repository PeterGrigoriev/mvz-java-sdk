package com.movilizer.connector;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMovilizerCloudSystem {
    int getSystemId();

    String getPassword();

    String getEndpoint();

    int getTimeout();
}
