package com.jy.common.excel;

import com.jy.common.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JunYu
 * @date 2025/3/8
 * @Description: 导入Excel
 */
@Slf4j
public class POIReaderUtil {

    //总行数
    private static int totalRows = 0;
    //总条数
    private static int totalCells = 0;

    public static <T> List<T> getExcelInfo(MultipartFile file, Class<T> clazz) {
        return getExcelInfo(file, null, clazz, null);
    }

    public static <T> List<T> getExcelInfo(MultipartFile file, Class<T> clazz, Integer rowIndex) {
        return getExcelInfo(file, null, clazz, rowIndex);
    }

    public static <T> List<T> getExcelInfo(MultipartFile file, Integer sheetIndex, Class<T> clazz) {
        return getExcelInfo(file, sheetIndex, clazz, null);
    }

    /**
     * 读EXCEL文件，获取信息集合
     * @param file excel文件
     * @param sheetIndex sheet页
     * @param clazz excel文件转换成具体的JAVA对象
     * @param <T> 返回的数据集合
     * @return
     */
    public static <T> List<T> getExcelInfo(MultipartFile file,Integer sheetIndex, Class<T> clazz, Integer rowIndex) {
        String fileName = file.getOriginalFilename();//获取文件名
        try {
            if(StringUtils.isEmpty(fileName)){
                return null;
            }
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            Workbook wb = createWorkbook(file.getInputStream(), extension);
            return readExcelValue(wb,clazz,rowIndex,sheetIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据文件扩展名创建对应的Workbook对象
     */
    private static Workbook createWorkbook(InputStream is, String extension) throws IOException {
        if ("xls".equalsIgnoreCase(extension)) {
            return new HSSFWorkbook(is);
        } else if ("xlsx".equalsIgnoreCase(extension)) {
            return new XSSFWorkbook(is);
        }
        throw new IllegalArgumentException("不支持的文件类型");
    }

    /**
     * 读取excel内容信息
     * @param wb excel对象
     * @param clazz excel文件转换成具体的JAVA对象
     * @param <T> 返回的数据集合
     * @return
     */
    private static <T> List<T> readExcelValue(Workbook wb,Class<T> clazz,Integer rowIndex,Integer sheetIndex) {
        //默认会跳过第一行标题
        int startRow = rowIndex == null ? 1 : rowIndex;
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(sheetIndex == null ? 0 : sheetIndex);
        // 得到Excel的行数
        totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > startRow && sheet.getRow(startRow-1) != null) {
            totalCells = sheet.getRow(startRow-1).getPhysicalNumberOfCells();
        }
        List<T> readerList = new ArrayList<>();
        //列字段
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        //过滤带有注解的Field,并根据注解进行排序
        List<Field> annotationFields = fields.stream().filter(filed -> filed.getAnnotation(BeanFieldAnnotation.class) != null)
                .sorted(Comparator.comparingInt(field -> field.getAnnotation(BeanFieldAnnotation.class).order())).collect(Collectors.toList());
        totalCells = Math.min(totalCells, annotationFields.size());
        // 循环Excel行数
        for (int r = startRow; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            //通过反射创建对象
            T instance = null;
            try {
                instance = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            // 循环Excel的列
            for (int c = 0; c <= totalCells-1; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    Field field = annotationFields.get(c);
                    //取消属性的访问控制权限
                    field.setAccessible(true);
                    String cellValue = getCellValue(cell);
                    try {
                        field.set(instance,cellValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            readerList.add(instance);
        }
        return readerList;
    }

    /**
     * 获取各个单元格的内容处理
     */
    private static String getCellValue(Cell cell) {
        String cellValue = "";
        // 判断数据的类型
        switch (cell.getCellType()) {
            case STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case NUMERIC: // 数值
                if(DateUtil.isCellDateFormatted(cell)){
                    //用于转化为日期格式
                    Date d = cell.getDateCellValue();
                    cellValue = DateTimeUtils.format(d,DateTimeUtils.YYYYMMDD_EN);
                    break;
                }
                DecimalFormat decimalFormat = new DecimalFormat("#.###########");
                cellValue = decimalFormat.format(BigDecimal.valueOf(cell.getNumericCellValue()));
                break;
            case BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: // 公式
                cellValue = String.valueOf(evaluateFormulaCell(cell));
                break;
            default:
                cellValue = getCellValueAsString(cell);
                break;
        }
        return cellValue;
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
