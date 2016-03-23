package com.movilizer.projectmanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ProjectSettingsNotAvailableException extends RuntimeException {
    public ProjectSettingsNotAvailableException(String projectName, int version) {
        super("Project Settings Not Available. Project name is [" + projectName + "], version is [" + version + "]");
    }
}
