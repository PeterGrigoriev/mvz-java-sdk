package com.movilizer.usermanagement;

import com.movilitas.movilizer.v14.MovilizerParticipant;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.movilizer.usermanagement.MovilizerUser.createUser;
import static com.movilizer.usermanagement.MovilizerUser.toParticipant;
import static com.movilizer.usermanagement.MovilizerUserInvitationMethod.*;
import static com.movilizer.usermanagement.MovilizerUserStatus.EXISTING;
import static com.movilizer.usermanagement.MovilizerUserStatus.NEW;
import static org.testng.Assert.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerUserTest {
    private static Map<String, String> fields = new HashMap<String, String>();
    
    @Test
    public void testToParticipant() throws Exception {
        IMovilizerUser user = new MovilizerUser(12345, "Some Name", EMAIL, "a@b.com", "001234567", NEW, fields);
        MovilizerParticipant participant = toParticipant(user);

        assertEquals(participant.getDeviceAddress(), "@a@b.com");
        assertEquals(participant.getName(), "Some Name");
        assertEquals(participant.getParticipantKey(), "12345");
    }

    @Test
    public void testGetPhone() {
        IMovilizerUser user = new MovilizerUser(12345, "Some Name", EMAIL, "a@b.com", null, NEW, fields);
        assertNull(user.getPhone());
        user = new MovilizerUser(12345, "Some Name", SMS, null, "+3362348767654", NEW, fields);
        Assert.assertEquals(user.getPhone(), "+3362348767654");
    }

    @Test
    public void testInvitationMethod() {
        IMovilizerUser user = new MovilizerUser(12345, "Some Name", EMAIL, "a@b.com", null, NEW, fields);
        Assert.assertEquals(user.getInvitationMethod(), EMAIL);

        user = new MovilizerUser(12345, "Some Name", SMS, null, "+3362348767654", NEW, fields);
        Assert.assertEquals(user.getInvitationMethod(), SMS);
    }

    @Test
    public void testEquals() {
        IMovilizerUser userOne = new MovilizerUser(12345, "Some Name", EMAIL, "a@b.com", null, NEW, fields);
        IMovilizerUser userTwo = new MovilizerUser(12345, "Some Name", SMS, null, "+3362348767654", NEW, fields);
        assertNotEquals(userOne, userTwo);

        IMovilizerUser userOneCopy = new MovilizerUser(12345, "Some Name", EMAIL, "a@b.com", null, NEW, fields);


        Assert.assertEquals(userOne, userOneCopy);
        Assert.assertEquals(userOne.hashCode(), userOneCopy.hashCode());


    }


    @Test
    public void testCreateInstance() throws Exception {
        IMovilizerUser userOne = createUser("a@b.com", "+712345678", "Alexander I", 12345, EMAIL, NEW, null);
        assertNotNull(userOne);
        assertEquals("@a@b.com", userOne.getDeviceAddress());

        IMovilizerUser userTwo = createUser("a@b.com", "+712345678", "Alexander II", 12345, SMS, NEW, null);
        assertEquals("+712345678", userTwo.getDeviceAddress());

        IMovilizerUser userThree = createUser(null, "+712345678", "Alexander III", 12345, SMS, NEW, null);
        assertEquals("+712345678", userThree.getDeviceAddress());

        try {
            createUser(null, "+712345678", "Alexander IV", 12345, EMAIL, NEW, null);
            fail("Must throw IllegalMovilizerUserException");
        } catch (IllegalMovilizerUserException e) {}

        try {
            createUser("", "+712345678", "Alexander IV", 12345, EMAIL, NEW, null);
            fail("Must throw IllegalMovilizerUserException");
        } catch (IllegalMovilizerUserException e) {}

        try {
            createUser("aaa", null, "Alexander IV", 12345, SMS, NEW, null);
            fail("Must throw IllegalMovilizerUserException");
        } catch (IllegalMovilizerUserException e) {}

        try {
            createUser("aaa", "", "Alexander IV", 12345, SMS, NEW, null);
            fail("Must throw IllegalMovilizerUserException");
        } catch (IllegalMovilizerUserException e) {}
    }

    @Test
    public void testSetStatusUpdatesTheField() {
        IMovilizerUser user = new MovilizerUser(123, "Peter", EMAIL, "a@b.com", null, NEW, new HashMap<String, String>());
        assertEquals(user.get(MovilizerUser.USER_STATUS), NEW.toString());
        user.setStatus(EXISTING);
        assertEquals(user.get(MovilizerUser.USER_STATUS), EXISTING.toString());
    }
}
