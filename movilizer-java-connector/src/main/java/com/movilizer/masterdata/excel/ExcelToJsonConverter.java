package com.movilizer.masterdata.excel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.movilizer.push.EventType;
import com.movilizer.util.json.JsonUtils;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ExcelToJsonConverter {

    private final ILogger logger = ComponentLogger.getInstance(ExcelToJsonConverter.class);

    public JsonArray convert(InputStream workbookInputStream, String idFieldName) throws IOException {
        return convert(workbookInputStream, new AddEventIdAndType(EventType.CREATE, idFieldName));
    }

    public JsonArray convert(InputStream workbookInputStream, Operation2<JsonObject, Integer> processRow) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(workbookInputStream);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        return convert(sheet, processRow);
    }


    public JsonArray convert(XSSFSheet sheet, Operation2<JsonObject, Integer> processRow) throws IOException {
        Iterator<Row> rowIterator = sheet.rowIterator();
        if(!rowIterator.hasNext()) {
            logger.error("Excel Sheet seems to be empty");
            throw new IOException("Excel Sheet seems to be empty. Cannot retrieve field names.");
        }
        List<String> fieldNames = toFieldNames(readStrings(rowIterator.next()));
        JsonArray result = new JsonArray();
        int i = 0;
        while (rowIterator.hasNext()) {
            List<String> values = readStrings(rowIterator.next());
            JsonObject object = JsonUtils.fromNamesAndValues(fieldNames, values);
            processRow.apply(object, i);
            result.add(object);
        }
        return result;
    }

    private List<String> toFieldNames(List<String> strings) {
        List<String> result = newArrayList();
        for (String string : strings) {
            result.add(string.replace(" ", "_"));
        }
        return result;
    }

    private List<String> readStrings(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();

        List<String> result = newArrayList();

        while (cellIterator.hasNext())
        {
            Cell cell = cellIterator.next();
            result.add(getStringValue(cell));
        }

        return result;
    }

    private String getStringValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
        }
        return cell.getStringCellValue();
    }

}
