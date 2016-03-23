package com.movilizer.projectmanagement;

import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.masterdata.IMasterdataSource;
import com.movilizer.pull.IMovilizerResponseObserver;
import com.movilizer.pull.IReplyMoveletProcessor;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.push.IMovilizerPushCallListener;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.util.template.ITemplateRepository;

import java.util.Collection;
import java.util.Set;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMovilizerProject extends IMobileProjectDescription {
    void onInitProject(IMovilizerPushCall call) throws Exception;

    void onShutdownProject(IMovilizerPushCall pushCall);

    void onUsersAssigned(Collection<IMovilizerUser> joinedUsers, IMovilizerPushCall pushCall);

    void onUsersUnassigned(Collection<IMovilizerUser> unassignedUsers, IMovilizerPushCall pushCall);

    IMovilizerPushCallListener onPushCallAvailable(IMovilizerPushCall call) throws Exception;

    Set<IReplyMoveletProcessor> getReplyProcessors();

    IMasterdataSource getMasterdataSource();

    Set<IMovilizerResponseObserver> getMovilizerResponseObservers();

    ITemplateRepository getTemplateRepository();


    IMobileProjectSettings getSettings() throws ProjectSettingsNotAvailableException;

    IMoveletKeyWithExtension getConfigurationMoveletKey();
}
