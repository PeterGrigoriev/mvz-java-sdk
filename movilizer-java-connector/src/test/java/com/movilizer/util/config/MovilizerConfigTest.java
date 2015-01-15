package com.movilizer.util.config;

import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Set;

import static com.movilizer.util.TestHelper.assertNotEmpty;
import static org.testng.Assert.*;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerConfigTest {
    private final IMovilizerConfig config;

    public MovilizerConfigTest() {
        config = MovilizerConfig.getInstance(MovilizerConfigTest.class);
        assertNotNull(config);
    }

    @Test
    public void testGetMovilizerSystem() throws Exception {
        IMovilizerCloudSystem movilizerSystem = config.getMovilizerSystem();
        assertNotNull(movilizerSystem);
        assertNotEmpty(movilizerSystem.getPassword(), "movilizerSystem.getPassword()");
        assertTrue(movilizerSystem.getSystemId() > 0, "system id cannot be 0");
        assertNotEmpty(movilizerSystem.getEndpoint(), "please specify endpoint");
        assertEquals(movilizerSystem.getTimeout(), 1800000);
    }



    @Test
    public void testGetMasterDataSystem() throws Exception {
        IMovilizerCloudSystem movilizerSystem = config.getMasterDataSystem();
        assertNotNull(movilizerSystem);
        assertNotEmpty(movilizerSystem.getPassword(), "movilizerSystem.getPassword()");
        assertTrue(movilizerSystem.getSystemId() > 0, "system id cannot be 0");
        assertNotEmpty(movilizerSystem.getEndpoint(), "please specify endpoint");
        assertEquals(movilizerSystem.getTimeout(), 1800000);
    }

    @Test
    public void testGetProxyInfo() {
        IProxyInfo proxyInfo = config.getProxyInfo();
        if (null != proxyInfo) {
            assertNotEmpty(proxyInfo.getProxyHost(), "ProxyHost");
            assertNotEmpty(proxyInfo.getProxyPort(), "ProxyPort");
        }
    }

    @Test
    public void testGetJdbcSettings() {
        IJdbcSettings jdbcSettings = config.getJdbcSettings();
        assertNotNull(jdbcSettings);
        assertNotEmpty(jdbcSettings.getUrl(), "jdbcSettings.getUrl()");
        assertNotEmpty(jdbcSettings.getUser(), "jdbcSettings.getUser()");
        assertNotEmpty(jdbcSettings.getPassword(), "jdbcSettings.getPassword()");
    }

    @Test
    public void testGetMoveletSettings() {
        IMoveletSettings moveletSettings = config.getMoveletSettings();
        assertNotNull(moveletSettings);
        assertNotEmpty(moveletSettings.getRootCategory().getName(), "root-category.name");
        assertTrue(moveletSettings.getRootCategory().getIcon() > 0);
        assertNotEmpty(moveletSettings.getNamespace(), "Movelet.Namespace");
        assertTrue(moveletSettings.getMasterDataSystemId() > 0);


        checkMoveletSettingsMasterData(moveletSettings);
    }

    @Test
    public void testReadMovilizerMaterDataSystems() {
        Set<IMovilizerMasterDataSystem> movilizerMaterDataSystems = ((MovilizerConfig) config).readMovilizerMaterDataSystems();
        assertEquals(2, movilizerMaterDataSystems.size());

    }

    private void checkMoveletSettingsMasterData(IMoveletSettings moveletSettings) {
        Set<IMovilizerMasterDataSystem> masterDataSystems = moveletSettings.getMasterDataSystems();
        assertEquals(masterDataSystems.size(), 2);
        assertEquals(moveletSettings.getMasterDataSystemId("poolOne").intValue(), 23456);
        assertEquals(moveletSettings.getMasterDataSystemId("poolTwo").intValue(), 23456);
        assertEquals(moveletSettings.getMasterDataSystemId("poolThree").intValue(), 267890);
        assertEquals(moveletSettings.getMasterDataSystemId("poolFour").intValue(), 267890);
        assertEquals(moveletSettings.getMasterDataSystemId("poolFive").intValue(), 267890);

        // defaults to master-data-system-id specified on the top level of movelet configuration
        assertEquals(moveletSettings.getMasterDataSystemId("someUnknownPool").intValue(), 54321);
    }

    @Test
    public void testGetPullSettings() {
        IMovilizerPullSettings pullSettings = config.getPullSettings();
        assertNotNull(pullSettings);
        assertTrue(pullSettings.getNumberOfReplies() > 0);
    }

    @Test
    public void testDatabaseSettingsAreOverwritten() {
        IJdbcSettings jdbcSettings = config.getJdbcSettings();
        assertTrue(jdbcSettings.getUser().contains("USER_NEW"));
    }

    @Test
    public void testMasterdataSettingsAreOverwritten() {
        Collection<IMasterdataXmlSetting> masterdataXmlSettings = config.getMasterdataXmlSettings();
        assertNotNull(masterdataXmlSettings);
        assertFalse(masterdataXmlSettings.isEmpty());
        IMasterdataXmlSetting xmlSetting = masterdataXmlSettings.iterator().next();
        assertEquals(xmlSetting.getPool(), "testPool");
    }

    @Test
    public void testMoveletSettingsAreOverwritten() {
        assertEquals(config.getMoveletSettings().getNamespace(), "TEST_NAMESPACE");
    }


    // TODO:
    @Test
    public void testPushSettingsAreOverwritten() {
        assertEquals(config.getPushSettings().getNumberOfNewUsersPerRun(), 33);
    }


}
