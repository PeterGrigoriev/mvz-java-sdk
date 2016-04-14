package com.movilizer.projectmanagement;

public enum MobileProjectEventType {
    INIT("init"),
    UPDATE("update"),
    SHUTDOWN("shutdown");

    private final String name;

    MobileProjectEventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MobileProjectEventType convertToMobileProjectEventType(String name) {
        MobileProjectEventType[] values = MobileProjectEventType.values();
        for (MobileProjectEventType mobileProjectEventType : values) {
            if (mobileProjectEventType.getName().equals(name)) {
                return mobileProjectEventType;
            }
        }
        return null;
    }
}
