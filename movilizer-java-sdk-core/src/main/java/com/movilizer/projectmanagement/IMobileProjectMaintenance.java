package com.movilizer.projectmanagement;

import com.google.inject.ImplementedBy;
import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MobileUserException;

import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com.
 */
@ImplementedBy(MobileProjectMaintenance.class)
public interface IMobileProjectMaintenance {
    IMobileProjectSettings getSettings(String projectName, int version);

    boolean assignMoveletsToAllUsers(IMovilizerCloudSystem system, IMoveletKeyWithExtension... moveletKeys) throws MobileUserException;

    boolean assignMovelets(IMovilizerCloudSystem pushCall, List<IMovilizerUser> mobileUsers, IMoveletKeyWithExtension[] moveletKeys);
}
