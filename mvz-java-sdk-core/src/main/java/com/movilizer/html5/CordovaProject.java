package com.movilizer.html5;

import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.projectmanagement.MovilizerProjectBase;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.push.IMovilizerPushCallListener;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.MovilizerConfig;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import static com.movilizer.connector.MoveletKeyWithExtension.keyWithExtension;
import static java.util.Arrays.asList;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public abstract class CordovaProject extends MovilizerProjectBase implements ICordovaProject {

    private final CordovaMoveletDataProvider dataProvider;

    public CordovaProject(String name, int version) {
        super(name, version);
        dataProvider = new CordovaMoveletDataProvider(this);
    }

    @Override
    public void onInitProject(IMovilizerPushCall call) throws Exception {
        call.addDocument(getDocumentPool(), getDocumentKey(), getZipFilePath());
        call.addMovelets(dataProvider, getMoveletTemplateName());
    }

    @Override
    public File getZipFilePath() {
        return new File(getBaseDirectory(), getZipFileName());
    }

    public String getMoveletTemplateName() {
        return "CordovaMovelet.vm";
    }

    public String getZipFileName() {
        return "www.zip";
    }

    public File getBaseDirectory() {
        URL codeSourceLocation = getClass().getProtectionDomain().getCodeSource().getLocation();
        try {
            return new File(codeSourceLocation.toURI()).getParentFile();
        } catch (URISyntaxException e) {
           return null;
        }
    }

    @Override
    public String getDocumentKey() {
        return getName();
    }

    @Override
    public String getDocumentPool() {
        return "HTML5_APPS";
    }

    @Override
    public void onShutdownProject(IMovilizerPushCall pushCall) {

    }

    @Override
    public IMoveletKeyWithExtension getConfigurationMoveletKey() {
        return keyWithExtension(dataProvider.getMoveletKey(), dataProvider.getMoveletKeyExtension());
    }

    @Override
    public void onUsersAssigned(Collection<IMovilizerUser> joinedUsers, IMovilizerPushCall pushCall) {
        pushCall.addAssignments(joinedUsers, asList(getConfigurationMoveletKey()));
    }

    @Override
    public void onUsersUnassigned(Collection<IMovilizerUser> unassignedUsers, IMovilizerPushCall pushCall) {

    }

    @Override
    public IMovilizerPushCallListener onPushCallAvailable(IMovilizerPushCall call) throws Exception {
        return null;
    }

    @Override
    public String getTitle() {
        return getName();
    }

    public String getMoveletKey() {
        return getName();
    }

    @Override
    public boolean isFullScreen() {
        IMovilizerConfig config = MovilizerConfig.getInstance(getClass());
        return config.getBoolean("movilizer.cordova.full-screen", false);
    }
}
