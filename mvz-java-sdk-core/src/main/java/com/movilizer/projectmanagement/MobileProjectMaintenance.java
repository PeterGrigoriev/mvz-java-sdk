package com.movilizer.projectmanagement;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IMovilizerRequestSender;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.push.MovilizerPushCall;
import com.movilizer.usermanagement.IMobileUserManager;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MobileUserException;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Peter.Grigoriev@movilizer.com.
 */
@Singleton
public class MobileProjectMaintenance implements IMobileProjectMaintenance {
    private final IProxyInfo proxyInfo;
    private final IMobileUserManager userManager;
    private final IMobileProjectManager projectManager;
    private final IMovilizerRequestSender requestSender;


    @Inject
    public MobileProjectMaintenance(IMobileUserManager userManager, IMobileProjectManager projectManager, IMovilizerRequestSender requestSender, @Nullable IProxyInfo proxyInfo) {
        this.userManager = userManager;
        this.projectManager = projectManager;
        this.requestSender = requestSender;
        this.proxyInfo = proxyInfo;
    }

    @Override
    public IMobileProjectSettings getSettings(String projectName, int version) {
        return projectManager.getMobileProjectSettings(projectName, version);
    }

    @Override
    public boolean assignMoveletsToAllUsers(IMovilizerCloudSystem system, IMoveletKeyWithExtension... moveletKeys) throws MobileUserException {
        return assignMovelets(system, userManager.getMobileUsers(), moveletKeys);
    }

    @Override
    public boolean assignMovelets(IMovilizerCloudSystem system, List<IMovilizerUser> mobileUsers, IMoveletKeyWithExtension[] moveletKeys) {
        MovilizerPushCall pushCall = new MovilizerPushCall(system, proxyInfo, requestSender, null, null);
        pushCall.addAssignments(mobileUsers, asList(moveletKeys));
        return pushCall.send();
    }
}
