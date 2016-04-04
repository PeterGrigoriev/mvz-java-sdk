package com.movilizer.rest;

import com.movilizer.projectmanagement.MovilizerProjectBase;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.push.IMovilizerPushCallListener;
import com.movilizer.usermanagement.IMovilizerUser;

import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class JsonAdaptorMoveletProject extends MovilizerProjectBase {

    INamedReaderProvider jsonSource;

    // TODO: introduce acknowledgement counterpart
    public JsonAdaptorMoveletProject(String name, int version) {
        super(name, version);
    }

    @Override
    public void onInitProject(IMovilizerPushCall call) throws Exception {

    }

    @Override
    public void onShutdownProject(IMovilizerPushCall pushCall) {

    }

    @Override
    public void onUsersAssigned(Collection<IMovilizerUser> joinedUsers, IMovilizerPushCall pushCall) {

    }

    @Override
    public void onUsersUnassigned(Collection<IMovilizerUser> unassignedUsers, IMovilizerPushCall pushCall) {

    }

    @Override
    public IMovilizerPushCallListener onPushCallAvailable(IMovilizerPushCall call) throws Exception {
        return null;
    }


}
