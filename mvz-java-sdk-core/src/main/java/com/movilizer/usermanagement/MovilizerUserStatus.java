package com.movilizer.usermanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum MovilizerUserStatus {
    NEW("O"),
    EXISTING("N");

    private final String code;

    private MovilizerUserStatus(String code) {
        this.code = code;
    }

    public static MovilizerUserStatus toUserStatus(String status) {
        if ("EXISTING".equalsIgnoreCase(status) || EXISTING.getCode().equalsIgnoreCase(status)) {
            return EXISTING;
        }
        if ("NEW".equalsIgnoreCase(status) || NEW.getCode().equalsIgnoreCase(status)) {
            return NEW;
        }
        return null;
    }


    public String getCode() {
        return code;
    }
}
