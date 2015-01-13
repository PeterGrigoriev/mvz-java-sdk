package com.movilizer.projectmanagement;

import com.google.inject.Inject;
import com.movilizer.acknowledgement.IMovilizerAcknowledgementCall;
import com.movilizer.acknowledgement.MovilizerAcknowledgmentCall;
import com.movilizer.assignmentmanagement.IMobileAssignmentEvent;
import com.movilizer.assignmentmanagement.IMobileAssignmentManager;
import com.movilizer.assignmentmanagement.MobileAssignmentEventType;
import com.movilizer.assignmentmanagement.MobileAssignmentException;
import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IMovilizerRequestSender;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.masterdata.IMasterdataAcknowledgementProcessor;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.masterdata.MasterdataAcknowledgementProcessor;
import com.movilizer.pull.*;
import com.movilizer.push.*;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.MovilizerConfig;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.movilizer.util.collection.CollectionUtils.take;
import static com.movilizer.util.dbc.Ensure.ensureNotNull;
import static java.lang.String.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MobileProjectRunner implements IMobileProjectRunner {

    private static final ILogger logger = ComponentLogger.getInstance("MobileProjectRunner");
    public static final String CANNOT_LOAD_PROJECT_MESSAGE = "Could not load mobile project implementation. Please check " +
            "that the corresponding JAR file exists and is not corrupt. " +
            "Project name is [%s], project version is [%d]";

    public static final String LOADED_PROJECT_MESSAGE = "Loaded mobile project. Project name is [%s], project version is [%d]";
    public static final String BACKEND_NOT_CONFIGURED_FOR_PROJECT_MESSAGE = "Connector contains a mobile project unknown for the backend. Please check your backend configuration. Project name is [%s], project version is [%d]";

    private final IMobileProjectManager projectManager;
    private final IMobileAssignmentManager assignmentManager;
    private final IMovilizerRequestSender requestSender;
    private final List<IMovilizerProject> mobileProjects;

    @Inject
    public MobileProjectRunner(IMobileProjectManager projectManager, IMobileProjectLoader projectLoader, IMobileAssignmentManager assignmentManager, IMovilizerRequestSender requestSender) throws MobileProjectException {
        this.projectManager = projectManager;
        this.assignmentManager = assignmentManager;
        this.requestSender = requestSender;
        mobileProjects = loadMobileProjects(projectLoader, projectManager);
        if (mobileProjects.isEmpty()) {
            logger.warn("No projects could be loaded. Connector is not effective. Please consider log messages of level TRACE for more information.");
        }
    }

    private List<IMovilizerProject> loadMobileProjects(IMobileProjectLoader projectLoader, IMobileProjectManager projectManager) throws MobileProjectException {
        Set<IMobileProjectDescription> projectDescriptions = projectLoader.getAvailableProjectDescriptions();
        Set<IMobileProjectSettings> projectSettings = new HashSet<IMobileProjectSettings>();

        for (IMobileProjectDescription description : projectDescriptions) {
            IMobileProjectSettings settings = projectManager.getMobileProjectSettings(description.getName(), description.getVersion());

            if (null == settings) {
                logger.warn(format(BACKEND_NOT_CONFIGURED_FOR_PROJECT_MESSAGE, description.getName(), description.getVersion()));
                continue;
            }
            projectSettings.add(settings);

        }

        List<IMovilizerProject> projects = new ArrayList<IMovilizerProject>();
        for (IMobileProjectSettings settings : projectSettings) {
            IMovilizerProject project = projectLoader.loadImplementation(settings);
            if (null == project) {
                logger.error(format(CANNOT_LOAD_PROJECT_MESSAGE, settings.getName(), settings.getVersion()));
                continue;
            }
            logger.debug(format(LOADED_PROJECT_MESSAGE, settings.getName(), settings.getVersion()));
            projects.add(project);
        }
        return projects;
    }

    @Override
    public void runPull() {
        for (IMovilizerProject mobileProject : mobileProjects) {
            runPull(mobileProject);
        }
    }

    @Override
    public void runPush() {
        for (IMovilizerProject mobileProject : mobileProjects) {
            try {
                runPush(mobileProject);
            } catch (Throwable e) {
                logger.error(format("Error running push phase for project [%s, %d]", mobileProject.getName(), mobileProject.getVersion()), e);
            }
        }
    }

    private void runPush(IMovilizerProject mobileProject) throws Exception {
        IMovilizerPushCall pushCall = new MovilizerPushCall(getCloudSystem(mobileProject), getProxyInfo(mobileProject), requestSender, null, mobileProject.getTemplateRepository());
        List<IMobileProjectEvent> projectEvents = projectManager.getMobileProjectEvents(mobileProject.getName(), mobileProject.getVersion());

        if (!projectEvents.isEmpty()) {
            // if there are project events, we only process them
            onProjectEventsPushCall(pushCall, mobileProject, projectEvents);
        } else {
            onRegularPushCall(mobileProject, pushCall);
        }
    }

    // TODO: pack it into ProjectSettings as well
    private IProxyInfo getProxyInfo(IMovilizerProject mobileProject) {
        return getConfig(mobileProject).getProxyInfo();
    }

    private IMovilizerConfig getConfig(IMovilizerProject mobileProject) {
        ensureNotNull(mobileProject, "mobileProject");
        return MovilizerConfig.getInstance(mobileProject.getClass());
    }

    private void onRegularPushCall(IMovilizerProject mobileProject, IMovilizerPushCall pushCall) throws Exception {
        IMovilizerPushCallListener assignmentPushCallListener = addAssignmentEvents(mobileProject, pushCall);

        IMovilizerPushCallListener projectSpecificPushCallListener = mobileProject.onPushCallAvailable(pushCall);
        if (null == projectSpecificPushCallListener) {
            projectSpecificPushCallListener = IMovilizerPushCallListener.VOID;
        }
        boolean success = pushCall.send();
        if (success) {
            logger.debug("Push call successfully done");
            assignmentPushCallListener.onSuccess();

            projectSpecificPushCallListener.onSuccess();
        } else {
            logger.error("Push call failed");
            assignmentPushCallListener.onFailure();
            projectSpecificPushCallListener.onFailure();
        }
    }

    private IMovilizerPushCallListener addAssignmentEvents(IMovilizerProject mobileProject, IMovilizerPushCall pushCall) throws MobileAssignmentException {
        IEventAcknowledgingPushCallListener acknowledger = new EventAcknowledgingPushCallListener(assignmentManager);
        List<IMobileAssignmentEvent> assignmentEvents = getMobileAssignmentEvents(mobileProject);


        List<IMovilizerUser> joinedUsers = new ArrayList<IMovilizerUser>();
        List<IMovilizerUser> unassignedUsers = new ArrayList<IMovilizerUser>();

        for (IMobileAssignmentEvent assignmentEvent : assignmentEvents) {
            acknowledger.addId(assignmentEvent.getId());
            IMovilizerUser user = assignmentEvent.getUser();
            if (assignmentEvent.getType() == MobileAssignmentEventType.ASSIGNED) {
                joinedUsers.add(user);
            } else {
                unassignedUsers.add(user);
            }
        }

        if (!joinedUsers.isEmpty()) {
            mobileProject.onUsersAssigned(joinedUsers, pushCall);
        }

        if (!unassignedUsers.isEmpty()) {
            mobileProject.onUsersUnassigned(unassignedUsers, pushCall);
        }

        return acknowledger;
    }

    private List<IMobileAssignmentEvent> getMobileAssignmentEvents(IMovilizerProject mobileProject) throws MobileAssignmentException {
        // TODO: propagate LIMIT to IMobileAssignmentManager
        List<IMobileAssignmentEvent> assignmentEvents = assignmentManager.getAssignmentEvents(mobileProject);
        IMovilizerConfig config = getConfig(mobileProject);
        assignmentEvents = take(assignmentEvents, config.getPushSettings().getNumberOfNewUsersPerRun());
        return assignmentEvents;
    }

    private void onProjectEventsPushCall(IMovilizerPushCall pushCall, IMovilizerProject mobileProject, List<IMobileProjectEvent> projectEvents) throws Exception {
        EventAcknowledgingPushCallListener listener = new EventAcknowledgingPushCallListener(projectManager);
        for (IMobileProjectEvent projectEvent : projectEvents) {
            listener.addId(projectEvent.getId());
            if (projectEvent.getType() == MobileProjectEventType.INIT) {
                mobileProject.onInitProject(pushCall);
            } else {
                mobileProject.onShutdownProject(pushCall);
            }
        }

        if (pushCall.send()) {
            listener.onSuccess();
        } else {
            listener.onFailure();
        }
    }

    private boolean runPull(IMovilizerProject mobileProject) {
        try {
            return createPullRunner(mobileProject).run();
        } catch (Throwable throwable) {
            logger.error(throwable);
            return false;
        }
    }


    private IMovilizerPullRunner createPullRunner(IMovilizerProject mobileProject) {
        IMovilizerCloudSystem system = getCloudSystem(mobileProject);
        IProxyInfo proxyInfo = getProxyInfo(mobileProject);

        IMovilizerPullCall pullCall = new MovilizerPullCall(system, proxyInfo, requestSender);
        IMovilizerAcknowledgementCall acknowledgementCall = new MovilizerAcknowledgmentCall(system, proxyInfo, requestSender);


        HashSet<IMasterdataXmlSetting> masterdataXmlSettings = getMasterdataXmlSettings(mobileProject);
        IMasterdataAcknowledgementProcessor masterdataAcknowledgementProcessor =
                new MasterdataAcknowledgementProcessor(masterdataXmlSettings, mobileProject.getMasterdataSource());


        Set<IMovilizerResponseObserver> observers = getReplyObservers(mobileProject);

        return new MovilizerPullRunner(pullCall,
                acknowledgementCall,
                mobileProject.getReplyProcessors(),
                observers,
                masterdataAcknowledgementProcessor);
    }

    private Set<IMovilizerResponseObserver> getReplyObservers(IMovilizerProject mobileProject) {
        IMoveletKeyWithExtension configMoveletKey = mobileProject.getConfigurationMoveletKey();
        if (null == configMoveletKey) {
            return mobileProject.getMovilizerResponseObservers();
        }

        Set<IMovilizerResponseObserver> observers = new HashSet<IMovilizerResponseObserver>(mobileProject.getMovilizerResponseObservers());

        observers.add(new ConfigurationMoveletLifecycleReplyObserver(projectManager, assignmentManager, mobileProject, configMoveletKey));

        return observers;
    }

    // TODO: pack it into ProjectSettings
    private HashSet<IMasterdataXmlSetting> getMasterdataXmlSettings(IMovilizerProject mobileProject) {
        return new HashSet<IMasterdataXmlSetting>(getConfig(mobileProject).getMasterdataXmlSettings());
    }


    private IMovilizerCloudSystem getCloudSystem(IMovilizerProject mobileProject) {
        return mobileProject.getSettings().getMoveletCloudSystem();
    }

}
