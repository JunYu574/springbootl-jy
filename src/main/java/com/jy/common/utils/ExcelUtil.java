package com.jy.common.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/6/16 11:33
 * @Description:
 * @Version: V1.0.0
 */
public class ExcelUtil {

    /**
     * @param dataList 导出数据集合
     * @param cls      数据类
     * @param out      输出流
     * @param headers  列名
     */
    public static XSSFWorkbook export(List<?> dataList, Class<?> cls, OutputStream out, String[] headers) {
        XSSFWorkbook workbook = new XSSFWorkbook(); // 创建一个新的Excel工作簿
        Sheet sheet = workbook.createSheet("Sheet1");   // 创建一个新的工作表

        // 创建表头
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 填充数据
        Cell cell;
        // 处理数据，塞入单元格
        List<Object[]> values = getRowValue(dataList, cls);
        for (int i = 0; i < values.size(); i++) {
            row = sheet.createRow(i + 1);
            Object[] objects = values.get(i);
            for (int j = 0; j < objects.length; j++) {
                cell = row.createCell(j);
                setCellValue(cell, objects[j]);
            }
        }

        try {
            //写出数据
            workbook.write(out);
            //关闭工作簿
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workbook;

    }

    /**
     * 行数据集合
     * @param dataList 数据集合
     * @param cls      集合类
     */
    private static List<Object[]> getRowValue(List<?> dataList, Class<?> cls) {
        List<Object[]> objects = new ArrayList<>();
        Method[] methods = getMethods(cls); // 获取属性get方法
        int cols = methods.length;   // 列数
        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = new Object[cols];
            for (int j = 0; j < cols; j++) {
                try {
                    obj[j] = methods[j].invoke(dataList.get(i)); // 反射获取属性值
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            objects.add(obj);
        }
        return objects;
    }

    /**
     * 获取属性get方法集合
     * @param cls 类类型
     */
    private static Method[] getMethods(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();   // 获取属性
        Method[] methods = new Method[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String property = fields[i].getName();  // 属性名
            String getMethodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
            try {
                methods[i] = cls.getMethod(getMethodName);  // 根据属性名获取get方法
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return methods;
    }

    /**
     * 获取属性名集合
     * @param cls 类类型
     */
    private String[] getProperties(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        String[] properties = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            // 将属性名转换为getter方法名，例如："name" -> "getName"
            properties[i] = fields[i].getName();
        }
        return properties;
    }

    /**
     * 判断值类型，转换后放入单元格
     * @param cell 单元格
     * @param invoke 值
     */
    private static void setCellValue(Cell cell, Object invoke) {
        if (invoke instanceof String) {
            cell.setCellValue((String) invoke);
        } else if (invoke instanceof Date) {
            cell.setCellValue((Date) invoke);
        } else if (invoke instanceof Boolean) {
            cell.setCellValue((Boolean) invoke);
        } else if (invoke instanceof Double) {
            cell.setCellValue((Double) invoke);
        } else if (invoke instanceof Calendar) {
            cell.setCellValue((Calendar) invoke);
        } else if (invoke instanceof RichTextString) {
            cell.setCellValue((RichTextString) invoke);
        } else {
            cell.setCellValue(String.valueOf(invoke));
        }
    }

}
