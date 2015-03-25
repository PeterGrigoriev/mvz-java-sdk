package com.movilizer.usermanagement.json;

import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MovilizerUserInvitationMethod;
import junit.framework.TestCase;

import java.util.List;

import static com.movilizer.util.resource.ResourceReaderProvider.newTestResourceReaderProvider;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileUserManagerTest extends TestCase {

    private JsonMobileUserManager userManager;

    @Override
    public void setUp() throws Exception {
        userManager = new JsonMobileUserManager(newTestResourceReaderProvider("/mobile-users.json"));
    }

    public void testGetMobileUsers() throws Exception {
        List<IMovilizerUser> mobileUsers = userManager.getMobileUsers();
        assertEquals(mobileUsers.size(), 2);
        IMovilizerUser user = mobileUsers.get(0);
        assertEquals(user.getDeviceAddress(), "@ivan.rebroff@gmail.com");
        assertEquals(user.getEmail(), "ivan.rebroff@gmail.com");
        assertEquals(user.getEmployeeNumber(), 111);
        assertEquals(user.getInvitationMethod(), MovilizerUserInvitationMethod.EMAIL);
        assertNull(user.getPhone());

    }
}