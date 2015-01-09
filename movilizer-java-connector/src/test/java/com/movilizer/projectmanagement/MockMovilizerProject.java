package com.movilizer.projectmanagement;

import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.connector.MoveletKeyWithExtension;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.push.IMovilizerPushCallListener;
import com.movilizer.usermanagement.IMovilizerUser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
* @author Peter.Grigoriev@movilizer.com
*/
public class MockMovilizerProject extends MovilizerProjectBase implements IMovilizerPushCallListener {
    private boolean isInitProjectCalled;
    private boolean isShutdownProjectCalled;
    private boolean isOnUsersAssignedCalled;
    private boolean isOnSuccessCalled;
    private boolean isOnFailureCalled;

    private Set<IMovilizerUser> joinedUsers = new HashSet<IMovilizerUser>();
    private Set<IMovilizerUser> unassignedUsers = new HashSet<IMovilizerUser>();
    private boolean pushCallAvailableCalled;

    private MockMovilizerResponseObserver responseObserver = new MockMovilizerResponseObserver();

    public MockMovilizerProject() {
        super("testProject", 1);
        addResponseObserver(responseObserver);
    }


    @Override
    public void onInitProject(IMovilizerPushCall call) throws Exception {
        isInitProjectCalled = true;
    }

    @Override
    public void onShutdownProject(IMovilizerPushCall pushCall) {
        isShutdownProjectCalled = true;
    }

    @Override
    public void onUsersAssigned(Collection<IMovilizerUser> joinedUsers, IMovilizerPushCall pushCall) {
        this.joinedUsers.addAll(joinedUsers);
        isOnUsersAssignedCalled = true;
    }

    @Override
    public void onUsersUnassigned(Collection<IMovilizerUser> unassignedUsers, IMovilizerPushCall pushCall) {
        this.unassignedUsers.addAll(joinedUsers);

    }

    @Override
    public IMovilizerPushCallListener onPushCallAvailable(IMovilizerPushCall call) throws Exception {
        this.pushCallAvailableCalled = true;
        return this;
    }

    @Override
    public void onSuccess() {
        this.isOnSuccessCalled = true;
    }

    @Override
    public void onFailure() {
        this.isOnFailureCalled = true;
    }

    public boolean isOnPushCallAvailableCalled() {
        return pushCallAvailableCalled;
    }

    public boolean isInitProjectCalled() {
        return isInitProjectCalled;
    }

    public boolean isShutdownProjectCalled() {
        return isShutdownProjectCalled;
    }

    public boolean isOnSuccessCalled() {
        return isOnSuccessCalled;
    }

    public boolean isOnFailureCalled() {
        return isOnFailureCalled;
    }

    public MockMovilizerResponseObserver getResponseObserver() {
        return responseObserver;
    }

    @Override
    public IMoveletKeyWithExtension getConfigurationMoveletKey() {
        return MoveletKeyWithExtension.keyWithExtension("MockProjectMovelet", "");
    }

    public boolean isOnUsersAssignedCalled() {
        return isOnUsersAssignedCalled;
    }

    public Set<IMovilizerUser> getJoinedUsers() {
        return joinedUsers;
    }
}
