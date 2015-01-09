package com.movilizer.usermanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class UserManagementException extends Exception {
    public UserManagementException(Throwable cause) {
        super(cause);
    }

    public UserManagementException(String message) {
        super(message);
    }
}
