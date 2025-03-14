package com.jy.common.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JunYu
 * @date 2025/3/8
 * @Description: Excel文件导入工具类
 */
public class ExcelImportUtil {

    /**
     * 读取Excel文件内容
     * @param file 要读取的Excel文件
     * @return 包含多个sheet数据的List，每个sheet对应一个List<Map>，Map的key为列标题，value为单元格值
     * @throws IOException 当文件不存在或读取失败时抛出
     */
    public static List<List<Map<String, Object>>> readExcel(File file) throws IOException {
        // 检查文件有效性
        if (!file.exists()) {
            throw new IOException("文件不存在");
        }

        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        try (
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = createWorkbook(fis, extension)
        ) {

            List<List<Map<String, Object>>> allSheetsData = new ArrayList<>();

            // 遍历所有sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                List<Map<String, Object>> sheetData = readSheet(sheet);
                allSheetsData.add(sheetData);
            }

            return allSheetsData;
        }
    }

    /**
     * 根据文件扩展名创建对应的Workbook对象
     */
    private static Workbook createWorkbook(FileInputStream fis, String extension) throws IOException {
        if ("xls".equalsIgnoreCase(extension)) {
            return new HSSFWorkbook(fis);
        } else if ("xlsx".equalsIgnoreCase(extension)) {
            return new XSSFWorkbook(fis);
        }
        throw new IllegalArgumentException("不支持的文件类型");
    }

    /**
     * 读取单个sheet的数据
     */
    private static List<Map<String, Object>> readSheet(Sheet sheet) {
        List<Map<String, Object>> sheetData = new ArrayList<>();
        List<String> headers = new ArrayList<>();

        // 遍历行（物理行号，跳过空行）
        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) continue;

            // 第一行作为列头
            if (rowIndex == 0) {
                headers = readHeaders(row);
                continue;
            }

            // 读取数据行
            Map<String, Object> rowData = readDataRow(row, headers);
            if (!rowData.isEmpty()) {
                sheetData.add(rowData);
            }
        }

        return sheetData;
    }

    /**
     * 读取列头信息
     */
    private static List<String> readHeaders(Row headerRow) {
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            String header = getCellValueAsString(cell);
            headers.add(header);
        }
        return headers;
    }

    /**
     * 读取数据行
     */
    private static Map<String, Object> readDataRow(Row row, List<String> headers) {
        Map<String, Object> rowData = new LinkedHashMap<>();

        for (int cellIndex = 0; cellIndex < headers.size(); cellIndex++) {
            String header = headers.get(cellIndex);
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            Object value = null;
            if (cell != null) {
                value = getCellValue(cell);
            }
            rowData.put(header, value);
        }

        return rowData;
    }

    /**
     * 获取单元格值（自动识别类型）
     */
    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return evaluateFormulaCell(cell);
            default:
                return getCellValueAsString(cell);
        }
    }

    /**
     * 处理公式单元格
     */
    private static Object evaluateFormulaCell(Cell cell) {
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return cell.getStringCellValue();
        }
    }

    /**
     * 获取单元格的字符串表示
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }
}
