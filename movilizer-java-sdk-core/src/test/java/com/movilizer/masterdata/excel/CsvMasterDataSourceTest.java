package com.movilizer.masterdata.excel;

import com.movilizer.masterdata.AcknowledgementStatus;
import com.movilizer.masterdata.IMasterdataReaderResult;
import com.movilizer.masterdata.MasterdataFieldNames;
import com.movilizer.masterdata.MasterdataXmlSettings;
import com.movilizer.masterdata.csv.CsvMasterDataSource;
import com.movilizer.util.resource.ResourceReaderProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.testng.Assert.*;

public class CsvMasterDataSourceTest {

    private CsvMasterDataSource masterDataSource;
    private MasterdataXmlSettings settings;

    @BeforeMethod
    public void setUp() throws Exception {
        masterDataSource = new CsvMasterDataSource(new ResourceReaderProvider("/excel/contacts.csv"), 0);
        MasterdataFieldNames masterdataFieldNames = new MasterdataFieldNames(null, "id", "lastName").jsonStyle();
        settings = new MasterdataXmlSettings("test", "contact", 100, 1, masterdataFieldNames);
    }

    @Test
    public void testRead() throws Exception {
        IMasterdataReaderResult readerResult = masterDataSource.read(settings);
        assertEquals(2, readerResult.getMasterdataPoolUpdate().getUpdate().size());

    }

    @Test
    public void testAcknowledge() throws Exception {
        masterDataSource.acknowledge(settings, newArrayList(1, 2), AcknowledgementStatus.ERROR);

    }
}