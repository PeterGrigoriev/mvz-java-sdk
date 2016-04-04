package com.movilizer.usermanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerUserChecker {
    private final IMovilizerUser user;

    public MovilizerUserChecker(IMovilizerUser user) {
        this.user = user;
    }

    public void run() throws IllegalMovilizerUserException {
        check(user.getInvitationMethod() != null, "Invitation method is not specified");
        if (user.getInvitationMethod() == MovilizerUserInvitationMethod.EMAIL) {
            check(!isNullOrEmpty(user.getEmail()), "Email not specified");
        } else {
            check(!isNullOrEmpty(user.getPhone()), "Phone not specified");
        }
    }

    private static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }


    private void check(boolean condition, String message) throws IllegalMovilizerUserException {
        if (!condition) {
            throw new IllegalMovilizerUserException(user, message);
        }
    }
}
