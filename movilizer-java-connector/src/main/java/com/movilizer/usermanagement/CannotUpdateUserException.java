package com.movilizer.usermanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author @author philippe.guillamet@altran.com
 */
public class CannotUpdateUserException extends UserManagementException {
    public CannotUpdateUserException(Exception e) {
        super(e);
    }

    public CannotUpdateUserException(Throwable throwable) {
        super(throwable);
    }
}
