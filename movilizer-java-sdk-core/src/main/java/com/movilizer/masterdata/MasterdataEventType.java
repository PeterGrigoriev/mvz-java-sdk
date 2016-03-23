package com.movilizer.masterdata;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum MasterdataEventType {
    DELETE, UPDATE;

    public static MasterdataEventType fromString(String s) {
        return s != null && s.toLowerCase().startsWith("d") ? DELETE : UPDATE;
    }
}
