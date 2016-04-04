package com.movilizer.usermanagement;

import com.google.common.base.Predicate;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author @author philippe.guillamet@altran.com
 */
public class MockMovilizerUserManager implements IMovilizerUserManager {
    private static Map<String, String> fields = new HashMap<String, String>();
    public static final IMovilizerUser MOVILIZER_USER_ONE = new MovilizerUser(1, "User One", MovilizerUserInvitationMethod.EMAIL, "a@b.com", null, MovilizerUserStatus.NEW, fields);
    public static final IMovilizerUser MOVILIZER_USER_TWO = new MovilizerUser(2, "User Two", MovilizerUserInvitationMethod.SMS, null, "+4912345678", MovilizerUserStatus.EXISTING, fields);
    private final List<IMovilizerUser> users;

    public MockMovilizerUserManager(List<IMovilizerUser> users) {
        this.users = users;
    }

    public MockMovilizerUserManager() {
        this(asList(
                MOVILIZER_USER_ONE,
                MOVILIZER_USER_TWO
        ));
    }

    public static IMovilizerUser getSomeUser() {
        IMovilizerUserManager userManager = new MockMovilizerUserManager();
        return userManager.getUsers().get(0);
    }

    @Override
    public List<IMovilizerUser> getUsers() {
        return users;
    }

    @Override
    public IMovilizerUser getUser(int employeeNumber) {
        for (IMovilizerUser user : users) {
            if (user.getEmployeeNumber() == employeeNumber) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<IMovilizerUser> getNewUsers() {
        List<IMovilizerUser> result = new ArrayList<IMovilizerUser>();
        for (IMovilizerUser user : users) {
            if (user.getStatus() != MovilizerUserStatus.EXISTING) {
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public void addUser(IMovilizerUser user) throws IllegalMovilizerUserException {
        users.add(user);
    }

    @Override
    public List<IMovilizerUser> getUsers(Predicate<IMovilizerUser> predicate) {
        List<IMovilizerUser> result = new ArrayList<IMovilizerUser>();
        for (IMovilizerUser user : users) {
            if (predicate.apply(user)) {
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public void reload() {
    }

    @Override
    public void save() {
    }

    @Override
    public void setUserStatusAndSave(Collection<IMovilizerUser> users, MovilizerUserStatus status) throws CannotUpdateUserException {
        for (IMovilizerUser user : users) {
            user.setStatus(status);
        }
    }

    @Override
    public boolean removeUser(IMovilizerUser newUser) {
        return users.remove(newUser);
    }
}
