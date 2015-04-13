package com.movilizer.masterdata.excel;

import com.google.gson.JsonArray;
import com.movilizer.util.resource.ResourceReaderProvider;
import org.testng.annotations.Test;

import java.io.Reader;

import static org.testng.Assert.*;

public class CsvToJsonConverterTest {

    @Test
    public void testConvert() throws Exception {
        CsvToJsonConverter converter = new CsvToJsonConverter();
        Reader reader = new ResourceReaderProvider("/excel/contacts.csv").get();
        JsonArray jsonArray = converter.convert(reader, "id", 0, 100);
        assertEquals(jsonArray.size(), 2);
    }
}