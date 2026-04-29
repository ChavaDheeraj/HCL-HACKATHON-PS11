package com.srm.ninjashop.utils;

import com.srm.ninjashop.model.TestUser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public final class ExcelUtils {

    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    private ExcelUtils() {
    }

    public static List<TestUser> getUsers(String filePath, String sheetName) {
        try (InputStream inputStream = ExcelUtils.class.getClassLoader().getResourceAsStream(filePath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            if (inputStream == null) {
                throw new IllegalStateException("Excel resource not found in classpath: " + filePath);
            }

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found in workbook: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Header row is missing in sheet: " + sheetName);
            }

            Map<String, Integer> headerIndexMap = buildHeaderIndexMap(headerRow);
            List<TestUser> users = new ArrayList<>();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null || isRowBlank(row)) {
                    continue;
                }

                users.add(new TestUser(
                        getCellValue(row, headerIndexMap, "first name"),
                        getCellValue(row, headerIndexMap, "last name"),
                        getCellValue(row, headerIndexMap, "email"),
                        getCellValue(row, headerIndexMap, "telephone"),
                        getCellValue(row, headerIndexMap, "password")));
            }

            return users;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read Excel test data from: " + filePath, exception);
        }
    }

    private static Map<String, Integer> buildHeaderIndexMap(Row headerRow) {
        Map<String, Integer> headerIndexMap = new HashMap<>();
        for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
            String header = DATA_FORMATTER.formatCellValue(headerRow.getCell(cellIndex))
                    .trim()
                    .toLowerCase(Locale.ENGLISH);
            if (!header.isEmpty()) {
                headerIndexMap.put(header, cellIndex);
            }
        }
        return headerIndexMap;
    }

    private static String getCellValue(Row row, Map<String, Integer> headerIndexMap, String columnName) {
        Integer columnIndex = headerIndexMap.get(columnName);
        if (columnIndex == null) {
            throw new IllegalArgumentException("Column not found in Excel sheet: " + columnName);
        }
        return DATA_FORMATTER.formatCellValue(row.getCell(columnIndex)).trim();
    }

    private static boolean isRowBlank(Row row) {
        for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
            if (!DATA_FORMATTER.formatCellValue(row.getCell(cellIndex)).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
