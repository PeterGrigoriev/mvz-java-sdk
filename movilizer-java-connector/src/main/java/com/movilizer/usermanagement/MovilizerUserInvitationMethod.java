package com.movilizer.usermanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum MovilizerUserInvitationMethod {
    SMS,
    EMAIL;


    /**
     * @param invitationMethod - either "SMS" or "EMAIL"
     * @return EMAIL, if invitationMethod is "EMAIL", SMS otherwise
     */
    public static MovilizerUserInvitationMethod toInvitationMethod(String invitationMethod) {
        return "EMAIL".equals(invitationMethod) ? EMAIL : SMS;
    }
}
