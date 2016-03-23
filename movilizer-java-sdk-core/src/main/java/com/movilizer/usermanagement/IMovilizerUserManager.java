package com.movilizer.usermanagement;

import com.google.common.base.Predicate;
import com.google.inject.ImplementedBy;

import java.util.Collection;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author @author philippe.guillamet@altran.com
 */
@Deprecated
@ImplementedBy(MovilizerUserManager.class)
public interface IMovilizerUserManager {
    List<IMovilizerUser> getUsers();

    IMovilizerUser getUser(int employeeNumber);

    List<IMovilizerUser> getNewUsers();

    void addUser(IMovilizerUser user) throws IllegalMovilizerUserException;

    List<IMovilizerUser> getUsers(Predicate<IMovilizerUser> predicate);


    void reload();

    void save();

    void setUserStatusAndSave(Collection<IMovilizerUser> users, MovilizerUserStatus status) throws CannotUpdateUserException;

    boolean removeUser(IMovilizerUser newUser);
}
