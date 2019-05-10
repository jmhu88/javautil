/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-backend
 * PackName: com.house365.finance.framework.util
 * FileName: ExcelUtil.java
 * Author: 邱刘军
 * Date: 2014年10月2日 上午9:38:57
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel文件导出工具类
 * 
 * @author 邱刘军
 * @date 2014年10月2日
 * @version 1.0
 */
public class ExcelUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtil.class);
    private static final int PAGESIZE = 1000;
    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 向浏览器客户端输出Excel文件
     * 
     * @param fileName
     * @param sheetName
     * @param titles
     * @param recordList
     * @param response
     */
    public static void exportExcel(String fileName, String sheetName, String[] titles, List<List<Object>> recordList,
            HttpServletResponse response) {
        exportExcel(fileName, sheetName, PAGESIZE, titles, recordList, response);
    }

    /**
     * 向浏览器客户端输出Excel文件
     * 
     * @param fileName
     * @param sheetName
     * @param pageSize
     * @param titles
     * @param recordList
     * @param response
     */
    public static void exportExcel(String fileName, String sheetName, int pageSize, String[] titles,
            List<List<Object>> recordList, HttpServletResponse response) {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFCellStyle headStyle = getHeadStyle(workBook);
        XSSFCellStyle bodyStyle = getBodyStyle(workBook);
        XSSFSheet sheet = null;
        int sheetCount = (int) Math.ceil(Double.valueOf(recordList.size()) / pageSize);
        sheetCount = sheetCount == 0 ? 1 : sheetCount;
        for (int i = 0; i < sheetCount; i++) {
            sheet = workBook.createSheet((i == 0) ? sheetName : (sheetName + "(" + (i + 1) + ")"));
            // 设置第一行表头的各个列标题
            XSSFRow headRow = sheet.createRow(0);
            XSSFCell cell = null;
            if (titles != null) {
                for (int j = 0; j < titles.length; j++) {
                    cell = headRow.createCell(j);
                    cell.setCellStyle(headStyle);
                    cell.setCellValue(titles[j]);
                }
            }
            // 设置第二行开始的动态列数据
            if (recordList != null) {
                int start = i * pageSize;
                int end = (i + 1) * pageSize;
                end = end > recordList.size() ? recordList.size() : end;
                for (int j = start, row = 1; j < end; j++, row++) {
                    XSSFRow bodyRow = sheet.createRow(row);
                    List<Object> record = recordList.get(j);
                    for (int k = 0; k < record.size(); k++) {
                        Object object = record.get(k);
                        if (object == null) {
                            object = "";
                        }
                        String value = null;
                        if (object instanceof Date) {
                            value = DateTimeUtils.getDateString((Date) object, DATEFORMAT);
                        } else {
                            value = String.valueOf(object);
                        }
                        cell = bodyRow.createCell(k);
                        cell.setCellStyle(bodyStyle);
                        cell.setCellValue(value);
                    }
                }
            }
        }
        // 输出文件并关闭输出流
        OutputStream os = null;
        try {
            if (!fileName.toLowerCase().endsWith(".xlsx")) {
                fileName += ".xlsx";
            }
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859_1");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType("application/octet-stream; charset=utf-8");
            os = response.getOutputStream();
            workBook.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置表头的单元格样式
     * 
     * @param workBook
     * @return
     */
    private static XSSFCellStyle getHeadStyle(XSSFWorkbook workBook) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = workBook.createCellStyle();
        // 设置单元格的背景颜色为淡蓝色
        cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        // 设置单元格左对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        // 设置单元格垂直居中对齐
        // cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        // cellStyle.setWrapText(true);
        // 设置单元格字体样式
        // XSSFFont font = wb.createFont();
        // // 设置字体加粗
        // font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        // font.setFontName("宋体");
        // font.setFontHeight((short) 200);
        // cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    /**
     * 设置表体的单元格样式
     * 
     * @param workBook
     * @return
     */
    private static XSSFCellStyle getBodyStyle(XSSFWorkbook workBook) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = workBook.createCellStyle();
        // 设置单元格左对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        // // 设置单元格垂直居中对齐
        // cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // // 创建单元格内容显示不下时自动换行
        // cellStyle.setWrapText(true);
        // // 设置单元格字体样式
        // XSSFFont font = wb.createFont();
        // // 设置字体加粗
        // font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        // font.setFontName("宋体");
        // font.setFontHeight((short) 200);
        // cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    public static List<String[]> getData(String fullName) {
        Workbook wb = null;
        List<String[]> dataList = new ArrayList<String[]>(100);
        try {
            InputStream inp = new FileInputStream(fullName);
            wb = WorkbookFactory.create(inp);
            for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
                int columnNum = 0;
                Sheet sheet = wb.getSheetAt(sheetIndex);
                if (sheet.getRow(0) != null) {
                    columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
                }
                if (columnNum > 0) {
                    for (Row row : sheet) {
                        String[] singleRow = new String[columnNum];
                        int n = 0;
                        for (int i = 0; i < columnNum; i++) {
                            Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_BLANK:
                                    singleRow[n] = "";
                                    break;
                                case Cell.CELL_TYPE_BOOLEAN:
                                    singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
                                    break;
                                // 数值
                                case Cell.CELL_TYPE_NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        singleRow[n] = String.valueOf(cell.getDateCellValue());
                                    } else {
                                        cell.setCellType(Cell.CELL_TYPE_STRING);
                                        String temp = cell.getStringCellValue();
                                        // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                                        if (temp.indexOf(".") > -1) {
                                            singleRow[n] = String.valueOf(new Double(temp)).trim();
                                        } else {
                                            singleRow[n] = temp.trim();
                                        }
                                    }
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    singleRow[n] = cell.getStringCellValue().trim();
                                    break;
                                case Cell.CELL_TYPE_ERROR:
                                    singleRow[n] = "";
                                    break;
                                case Cell.CELL_TYPE_FORMULA:
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    singleRow[n] = cell.getStringCellValue();
                                    if (singleRow[n] != null) {
                                        singleRow[n] = singleRow[n].replaceAll("#N/A", "").trim();
                                    }
                                    break;
                                default:
                                    singleRow[n] = "";
                                    break;
                            }
                            n++;
                        }
                        if ("".equals(singleRow[0])) {
                            continue;
                        }// 如果第一行为空，跳过
                        dataList.add(singleRow);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /******************** 97-2003版xls ********************/

    /**
     * 构建一个简单的xls
     * 
     * @param workBook
     * @param list
     * @author hzy
     */
    public static void buildSimpleXls(HSSFWorkbook workBook, List<Object[]> list) {
        HSSFSheet sheet = workBook.createSheet();
        HSSFRow row = null;
        HSSFCell cell = null;
        Object[] arr;
        for (int i = 0; i < list.size(); i++) {
            arr = list.get(i);
            row = sheet.createRow(i);
            for (int j = 0; j < arr.length; j++) {
                cell = row.createCell(j);
                if (arr[j] == null) {
                    continue;
                } else if (arr[j] instanceof BigDecimal) {
                    cell.setCellValue(((BigDecimal) arr[j]).doubleValue());
                } else if (arr[j] instanceof Double) {
                    cell.setCellValue((Double) arr[j]);
                } else if (arr[j] instanceof String) {
                    cell.setCellValue((String) arr[j]);
                } else if (arr[j] instanceof Integer) {
                    cell.setCellValue((Integer) arr[j]);
                } else {
                    cell.setCellValue(arr[j].toString());
                    LOG.error("错误的数据格式：" + arr[j].toString());
                }
            }
        }
    }

    /**
     * http下载xls文件
     * 
     * @param workBook
     * @param response
     * @param fileName
     * @author hzy
     */
    public static void httpDownloadXls(HSSFWorkbook workBook, HttpServletResponse response, String fileName) {
        OutputStream os = null;
        try {
            if (!fileName.endsWith(".xls")) {
                fileName += ".xls";
            }
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859_1");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType("application/octet-stream; charset=utf-8");
            os = response.getOutputStream();
            workBook.write(os);
            os.flush();
        } catch (IOException e) {
            LOG.error("下载xls文件异常[" + fileName + "]：", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    LOG.error("关闭下载输出流异常：", e);
                }
            }
        }
    }

}