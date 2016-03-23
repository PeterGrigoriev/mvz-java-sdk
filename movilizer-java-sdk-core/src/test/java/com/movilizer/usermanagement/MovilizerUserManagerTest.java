package com.movilizer.usermanagement;

import com.movilizer.util.config.MovilizerConfig;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.movilizer.usermanagement.MovilizerUserInvitationMethod.EMAIL;
import static com.movilizer.usermanagement.MovilizerUserStatus.NEW;
import static com.movilizer.usermanagement.XmlConfigurationNode.getConfigurationProperty;
import static java.lang.Integer.parseInt;
import static org.testng.Assert.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerUserManagerTest {

    @SuppressWarnings("deprecation")
    private final IMovilizerUserManager userManager;
    private Map<String, String> fields = new HashMap<String, String>();

    public MovilizerUserManagerTest() {
        userManager = new MovilizerUserManager(MovilizerConfig.getInstance(MovilizerUserManagerTest.class).getPushSettings(), getClass());
    }

    @BeforeMethod
    public void setUp() throws Exception {
        userManager.reload();
    }

    @Test
    public void testToXmlString() throws Exception {
        Map<String, String> additionalFields = new HashMap<String, String>();
        additionalFields.put("fieldOne", "one");
        additionalFields.put("fieldTwo", "two");

        IMovilizerUser user = new MovilizerUser(1234, "Ivan Morkovkin", MovilizerUserInvitationMethod.SMS, "a.b@com", "+33123456", NEW, additionalFields);
        String xml = MovilizerUserManager.toXmlString(user);
        assertNotNull(xml);
        assertTrue(xml.contains("<fieldOne>one</fieldOne>"));
    }

    @Test
    public void testGetUsers() throws Exception {
        List<IMovilizerUser> users = userManager.getUsers();

        assertNotNull(users);
        assertFalse(users.isEmpty());

        for (IMovilizerUser user : users) {
            checkUser(user);
        }
    }

    @Test
    public void testAddUser() throws IllegalMovilizerUserException {
        IMovilizerUser newUser = new MovilizerUser(1234, "New Test User", EMAIL, "a@b.com", null, NEW, fields);
        int oldSize = userManager.getUsers().size();
        userManager.addUser(newUser);
        int newSize = userManager.getUsers().size();
        assertEquals(oldSize + 1, newSize);
    }

    @Test
    public void testAddUserWithSameEmployeeNumber() {
        IMovilizerUser user = userManager.getUsers().get(0);
        try {
            userManager.addUser(new MovilizerUser(user.getEmployeeNumber(), "New Test User", EMAIL, "a@b.com", null, NEW, fields));
            fail("Should have thrown IllegalUserException");
        } catch (IllegalMovilizerUserException e) {
            // wanted
        }
    }


    @Test
    public void testAddUserWithSameDeviceAddress() {
        IMovilizerUser user = userManager.getUsers().get(0);
        try {
            userManager.addUser(new MovilizerUser(1234567, "New Test User", user.getInvitationMethod(), user.getEmail(), user.getPhone(), NEW, fields));
            fail("Should have thrown IllegalUserException");
        } catch (IllegalMovilizerUserException e) {
            // wanted
        }
    }

    @Test
    public void testAddUserWithSaveAndReload() throws IOException, ConfigurationException, IllegalMovilizerUserException {
        IMovilizerUser newUser = new MovilizerUser(1234, "New Test User", EMAIL, "a@b.com", null, NEW, fields);
        int oldSize = userManager.getUsers().size();
        userManager.addUser(newUser);
        userManager.reload();
        assertEquals(oldSize, userManager.getUsers().size());

        userManager.addUser(newUser);
        userManager.save();
        userManager.reload();
        int newSize = userManager.getUsers().size();
        assertEquals(oldSize + 1, newSize);

        boolean removed = userManager.removeUser(newUser);
        assertTrue(removed);
        userManager.save();
        userManager.reload();
        newSize = userManager.getUsers().size();
        assertEquals(oldSize, newSize);
    }

    @Test
    public void testToConfiguration() {
        IMovilizerUser user = userManager.getUsers().get(0);
        ConfigurationNode node = MovilizerUserManager.userToConfigurationNode(user);
        assertNotNull(node);
        assertEquals(user.getName(), getConfigurationProperty(node, MovilizerUser.NAME));
        assertEquals(user.getEmail(), getConfigurationProperty(node, MovilizerUser.EMAIL));
        assertEquals(user.getPhone(), getConfigurationProperty(node, MovilizerUser.PHONE));
        int configurationEmployeeNumber = parseInt(getConfigurationProperty(node, MovilizerUser.EMPLOYEE_NUMBER).toString());
        assertEquals(user.getEmployeeNumber(), configurationEmployeeNumber);
        assertEquals(user.getInvitationMethod().toString(), getConfigurationProperty(node, MovilizerUser.INVITATION_METHOD));
        assertEquals(user.getStatus().toString(), getConfigurationProperty(node, MovilizerUser.USER_STATUS));
    }


    @Test
    public void testGetAdditionalFields() {
        IMovilizerUser user = userManager.getUsers().get(0);
        String service = user.get("service");
        assertEquals(service, "XOM");
    }
    
    
    private void checkUser(IMovilizerUser user) throws IllegalMovilizerUserException {
        new MovilizerUserChecker(user).run();
    }
}
