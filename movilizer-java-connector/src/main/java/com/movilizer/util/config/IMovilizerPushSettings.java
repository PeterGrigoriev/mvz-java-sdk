package com.movilizer.util.config;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMovilizerPushSettings {
    boolean sendConfigurationMovelets();

    boolean resendAllMovelets();

    int getNumberOfNewUsersPerRun();
}
