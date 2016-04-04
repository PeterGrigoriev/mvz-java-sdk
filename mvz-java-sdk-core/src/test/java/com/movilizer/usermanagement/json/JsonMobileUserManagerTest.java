package com.movilizer.usermanagement.json;

import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MovilizerUserInvitationMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.movilizer.util.resource.ResourceReaderProvider.newResourceReaderProvider;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Peter.Grigoriev@gmail.com.
 */
public class JsonMobileUserManagerTest  {

    private JsonMobileUserManager userManager;

    @BeforeMethod
    public void setUp() throws Exception {
        userManager = new JsonMobileUserManager(newResourceReaderProvider("/mobile-users.json"));
    }

    @Test
    public void testGetMobileUsers() throws Exception {
        List<IMovilizerUser> mobileUsers = userManager.getMobileUsers();
        assertEquals(mobileUsers.size(), 2);
        IMovilizerUser user = mobileUsers.get(0);
        assertEquals(user.getDeviceAddress(), "@ivan.rebroff@gmail.com");
        assertEquals(user.getEmail(), "ivan.rebroff@gmail.com");
        assertEquals(user.getEmployeeNumber(), 111);
        assertEquals(user.getInvitationMethod(), MovilizerUserInvitationMethod.EMAIL);
        assertNull(user.getPhone());


        assertEquals(user.get("team.id"), "22");
        assertEquals(user.get("team.name"), "dream team");

    }
}