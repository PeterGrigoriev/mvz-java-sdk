package com.movilizer.usermanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author @author philippe.guillamet@altran.com
 */
public class CannotConvertToUserStatusException extends CannotCreateUserException {
    public CannotConvertToUserStatusException(String message) {
        super(message);
    }
}
