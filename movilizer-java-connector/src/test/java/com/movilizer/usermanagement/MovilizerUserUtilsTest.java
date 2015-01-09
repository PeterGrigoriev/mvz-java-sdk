package com.movilizer.usermanagement;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerUserUtilsTest {
    @Test
    public void testUserToShortString() throws Exception {
        String str = MovilizerUserUtils.usersToShortString(new MockMovilizerUserManager().getUsers(), ", ");
        assertNotNull(str);
        assertFalse(str.isEmpty());
    }
}
