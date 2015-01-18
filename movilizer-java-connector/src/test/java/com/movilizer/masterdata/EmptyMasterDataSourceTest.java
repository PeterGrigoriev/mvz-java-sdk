package com.movilizer.masterdata;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertNull;

public class EmptyMasterDataSourceTest {

    private MasterdataXmlSettings setting;
    private EmptyMasterDataSource masterDataSource;

    @BeforeMethod
    public void setUp() throws Exception {
        setting = new MasterdataXmlSettings("test", "poolOne", 10, 1, new MasterdataFieldNames("g", "o", "test"));
        masterDataSource = new EmptyMasterDataSource();
    }

    @Test
    public void testRead() throws Exception {
        assertNull(masterDataSource.read(setting));
    }

    @Test
    public void testAcknowledge() throws Exception {
        assertNull(new EmptyMasterDataSource().read(setting));
        masterDataSource.acknowledge(setting, Arrays.asList(1,2), AcknowledgementStatus.SENT);
    }
}