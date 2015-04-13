package com.movilizer.masterdata.excel;

import com.google.gson.JsonArray;
import com.movilizer.util.resource.ResourceReaderProvider;
import org.testng.annotations.Test;

import java.io.Reader;

import static com.movilizer.util.resource.ResourceReaderProvider.newResourceReader;
import static org.testng.Assert.*;

public class CsvToJsonConverterTest {

    @Test
    public void testConvert() throws Exception {
        CsvToJsonConverter converter = new CsvToJsonConverter();
        Reader reader = newResourceReader("/excel/contacts.csv");
        JsonArray jsonArray = converter.convert(reader, "id", 0, 100);
        assertEquals(jsonArray.size(), 2);
    }
}