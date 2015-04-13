package com.movilizer.masterdata.excel;

import com.movilizer.masterdata.IMasterdataReaderResult;
import com.movilizer.masterdata.MasterdataFieldNames;
import com.movilizer.masterdata.MasterdataXmlSettings;
import com.movilizer.util.resource.ResourceReaderProvider;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.Reader;

import static org.testng.Assert.*;

public class ExcelMasterDataSourceTest {
    private final MasterdataXmlSettings settings;

    public ExcelMasterDataSourceTest() {
        MasterdataFieldNames fieldNames = new MasterdataFieldNames(null, "id", "lastName");
        fieldNames.setEventId("eventId");
        fieldNames.setEventType("eventType");
        settings = new MasterdataXmlSettings("test", "contact", 100, 1,
                fieldNames);
    }

    @Test
    public void testRead() throws Exception {
        InputStream stream = getClass().getResourceAsStream("/excel/contacts.xlsx");
        ExcelMasterDataSource excelMasterDataSource = new ExcelMasterDataSource(stream);
        IMasterdataReaderResult result = excelMasterDataSource.read(settings);
        assertNotNull(result);
        assertEquals(result.getMasterdataPoolUpdate().getUpdate().size(), 2);
    }
}