package com.movilizer.usermanagement.json;

import com.movilizer.assignmentmanagement.IMobileAssignmentEvent;
import com.movilizer.projectmanagement.MovilizerProjectBase;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.push.IMovilizerPushCallListener;
import com.movilizer.usermanagement.IMovilizerUser;
import org.testng.annotations.BeforeTest;

import java.util.Collection;
import java.util.List;

import static com.movilizer.util.resource.ResourceReaderProvider.newResourceReaderProvider;
import static org.testng.Assert.assertEquals;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileAssignmentManagerTest {

    private JsonMobileAssignmentManager assignmentManager;

    @BeforeTest
    public void setUp() throws Exception {
        assignmentManager = new JsonMobileAssignmentManager(newResourceReaderProvider("/mobile-assignment-events.json"));
    }


    MovilizerProjectBase project = new MovilizerProjectBase("someMobileApp", 22) {
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

    public void testGetAssignmentEvents() throws Exception {
        List<IMobileAssignmentEvent> assignmentEvents = assignmentManager.getAssignmentEvents(project);
        assertEquals(assignmentEvents.size(), 1);
    }
}