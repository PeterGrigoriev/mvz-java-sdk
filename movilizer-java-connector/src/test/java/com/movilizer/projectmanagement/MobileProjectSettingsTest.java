package com.movilizer.projectmanagement;

import com.movilizer.connector.MovilizerCloudSystem;
import junit.framework.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MobileProjectSettingsTest {

    @Test
    public void testEquals() throws Exception {
        MobileProjectSettings settingsOne = new MobileProjectSettings();
        settingsOne.setId(1);
        settingsOne.setMasterDataCloudSystem(new MovilizerCloudSystem(1, "p", "e", 1200));
        settingsOne.setName("ProjectOne");
        settingsOne.setVersion(1);

        MobileProjectSettings settingsTwo = new MobileProjectSettings();
        settingsTwo.setId(1);
        settingsTwo.setMasterDataCloudSystem(new MovilizerCloudSystem(1, "p", "e", 1200));
        settingsTwo.setName("ProjectOne");
        settingsTwo.setVersion(1);
        assertEquals(settingsOne, settingsTwo);
    }

    @Test
    public void testGetId() {
        MobileProjectSettings settingsOne = new MobileProjectSettings();
        settingsOne.setId(1);
        assertEquals(settingsOne.getId(), 1);

    }

    @Test
    public void testGetMasterDataCloudSystem() throws Exception {

        MobileProjectSettings settingsOne = new MobileProjectSettings();
        Assert.assertNull(settingsOne.getMasterDataCloudSystem());

    }
}