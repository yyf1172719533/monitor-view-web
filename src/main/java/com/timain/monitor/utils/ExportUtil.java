package com.timain.monitor.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Stone
 * @version 1.0
 * @since 2022-05-19
 */
public final class ExportUtil {

    /**
     * 下载Excel文件
     *
     * @param fileName
     * @param titles
     * @param columns
     * @param data
     * @param response
     * @throws IOException
     */
    public static void downloadExcel(String fileName, String[] titles, String[] columns, List<Map<String, Object>> data, HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = createHSSFWorkbook(fileName, titles, columns, data);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/ms-excel");
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "inline;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1") + ".xls");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        wb.write(response.getOutputStream());

    }


    /**
     * 生成文件
     *
     * @param sheetName
     * @param title
     * @param column
     * @param values
     * @return
     */
    public static HSSFWorkbook createHSSFWorkbook(String sheetName, String[] title, String[] column, List<Map<String, Object>> values) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < column.length; j++) {
                //将内容按顺序赋给对应的列对象

                if (values.get(i).get((column[j])) != null) {

                    row.createCell(j).setCellValue(String.valueOf(values.get(i).get((column[j]))));
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }
        try {
            setSheetColumnWidth(sheet);

        } catch (Exception e) {
        }
        return workbook;
    }

    public static void setSheetColumnWidth(Sheet sheet) throws Exception {
        // 根据你数据里面的记录有多少列，就设置多少列
        int numberOfCells = sheet.getRow(0).getPhysicalNumberOfCells();
        for (int i = 0; i < numberOfCells; i++) {
            //调整第一列宽度
            sheet.autoSizeColumn((short) i);
        }
    }

}
