package com.movilizer.usermanagement;

import java.util.Set;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMovilizerUser {

    String getEmail();

    String getPhone();

    int getEmployeeNumber();

    String getName();

    MovilizerUserInvitationMethod getInvitationMethod();

    String getDeviceAddress();

    MovilizerUserStatus getStatus();

    void setStatus(MovilizerUserStatus status);

    String get(String fieldName);

    Set<String> getFieldNames();
}
