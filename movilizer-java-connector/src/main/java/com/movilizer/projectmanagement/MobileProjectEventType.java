package com.movilizer.projectmanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum MobileProjectEventType {
    INIT, UPDATE, SHUTDOWN;

    public static MobileProjectEventType fromString(String value) {
        if ("init".equals(value.toLowerCase())) {
            return INIT;
        }
        if ("update".equals(value.toLowerCase())) {
            return UPDATE;
        }
        return SHUTDOWN;
    }
}
