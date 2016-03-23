package com.movilizer.usermanagement;

import static com.movilizer.usermanagement.MovilizerUserUtils.userToLongString;
import static java.text.MessageFormat.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class IllegalMovilizerUserException extends UserManagementException {
    public IllegalMovilizerUserException(IMovilizerUser user, String message) {
        super(format("{0} [{1}]", message, userToLongString(user)));
    }
}
