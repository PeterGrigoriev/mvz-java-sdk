package com.movilizer.projectmanagement;

import com.movilizer.pull.MockReplyProcessor;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.push.IMovilizerPushCallListener;
import com.movilizer.usermanagement.IMovilizerUser;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.Assert.assertNotNull;

public class MovilizerProjectBaseTest {

    private MovilizerProjectBase project;

    @BeforeMethod
    public void setUp() throws Exception {
        project = new MovilizerProjectBase("projectOne", 22) {
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
        };
    }

    @Test(expectedExceptions = ProjectSettingsNotAvailableException.class)
    public void testGetSettingsThrows() throws Exception {
        project.getSettings();
    }

    @Test
    public void testGetSettings() throws Exception {
        project.setProjectSettings(new MobileProjectSettings());
        assertNotNull(project.getSettings());
    }

    @Test
    public void testAddReplyProcessor() {
        project.addReplyProcessor(new MockReplyProcessor("mov", false));
        Assert.assertFalse(project.getReplyProcessors().isEmpty());
    }

}