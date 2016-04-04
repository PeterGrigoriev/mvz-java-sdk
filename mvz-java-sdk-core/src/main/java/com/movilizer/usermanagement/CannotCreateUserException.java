package com.movilizer.usermanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author @author philippe.guillamet@altran.com
 */
public class CannotCreateUserException extends Exception {
    public CannotCreateUserException(String message) {
        super(message);
    }
}
