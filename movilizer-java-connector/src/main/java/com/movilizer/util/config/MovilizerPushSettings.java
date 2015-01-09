package com.movilizer.util.config;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerPushSettings implements IMovilizerPushSettings {
    private final boolean sendConfigurationMovelets;
    private final boolean resendAllMovelets;
    private final int numberOfNewUsersPerRun;

    public static final int DEFAULT_NUMBER_OF_NEW_USERS_PER_RUN = 50;

    public MovilizerPushSettings(boolean sendConfigurationMovelets, boolean resendAllMovelets) {
        this(sendConfigurationMovelets, resendAllMovelets, DEFAULT_NUMBER_OF_NEW_USERS_PER_RUN);
    }

    public MovilizerPushSettings(boolean sendConfigurationMovelets, boolean resendAllMovelets, int numberOfNewUsersPerRun) {
        this.sendConfigurationMovelets = sendConfigurationMovelets;
        this.resendAllMovelets = resendAllMovelets;
        this.numberOfNewUsersPerRun = numberOfNewUsersPerRun;
    }

    public boolean sendConfigurationMovelets() {
        return sendConfigurationMovelets;
    }

    @Override
    public boolean resendAllMovelets() {
        return resendAllMovelets;
    }

    @Override
    public int getNumberOfNewUsersPerRun() {
        return numberOfNewUsersPerRun;
    }
}
