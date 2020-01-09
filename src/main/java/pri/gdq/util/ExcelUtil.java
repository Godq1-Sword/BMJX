package pri.gdq.util;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Map;

/**
 * program : taxoa-backend
 * description: POI导出Excel
 *
 * @author : gdq
 * data : 2019-12-26 15:22
 **/
public class ExcelUtil {
    public static Workbook writeExcel(String sheetName,
                                      Map<String, Object> sheetData) {
        Workbook workbook = new XSSFWorkbook();
        CellStyle titleCellStyle = createTitleStyle(workbook);
        CellStyle commCellStyle = createCommStyle(workbook);
        int index = -1;
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(++index);
        Cell cell = row.createCell(0);
        cell.setCellValue(sheetName);
        cell.setCellStyle(titleCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 11));
        index++;
        row = sheet.createRow(++index);
        createCell(row, 0, "工号", commCellStyle);
        createCell(row, 1, sheetData.get("username").toString(), commCellStyle);
        createCell(row, 4, "姓名", commCellStyle);
        createCell(row, 5, sheetData.get("name").toString(), commCellStyle);
        createCell(row, 8, "日期", commCellStyle);
        createCell(row, 9, sheetData.get("startTime").toString(), commCellStyle);
        createCell(row, 10, " - ", commCellStyle);
        createCell(row, 11, sheetData.get("endTime").toString(), commCellStyle);
        row = sheet.createRow(++index);
        createCell(row, 0, "工作日数", commCellStyle);
        createCell(row, 2, "迟到次数", commCellStyle);
        createCell(row, 3, "早退次数", commCellStyle);
        createCell(row, 4, "平均工时", commCellStyle);
        createCell(row, 5, "出差天数", commCellStyle);
        createCell(row, 6, "外勤天数", commCellStyle);
        createCell(row, 7, "请假天数", commCellStyle);
        createCell(row, 8, "旷工天数", commCellStyle);
        createCell(row, 9, "缺卡次数", commCellStyle);
        createCell(row, 10, "补卡次数", commCellStyle);
        createCell(row, 11, "补假次数", commCellStyle);
        int col = 0;
        sheet.addMergedRegion(new CellRangeAddress(index, index, col, ++col));
        for (; col < 11; ) {
            sheet.addMergedRegion(new CellRangeAddress(index, index + 1, ++col, col));
        }
        row = sheet.createRow(++index);
        createCell(row, 0, "标准", commCellStyle);
        createCell(row, 1, "实际", commCellStyle);
        row = sheet.createRow(++index);
        // 总计天数
        createCell(row, 0, sheetData.get("workTotalDay").toString(), commCellStyle);
        // 出勤天数
        createCell(row, 1, sheetData.get("workRealDay").toString(), commCellStyle);
        // 迟到次数
        createCell(row, 2, sheetData.get("lateCount").toString(), commCellStyle);
        // 早退次数
        createCell(row, 3, sheetData.get("earlyCount").toString(), commCellStyle);
        // 平均工时
        createCell(row, 4, sheetData.get("avgWorkTime").toString(), commCellStyle);
        // 出差天数
        createCell(row, 5, sheetData.get("businsessCount").toString(), commCellStyle);
        // 外勤天数
        createCell(row, 6, sheetData.get("workCount").toString(), commCellStyle);
        // 请假天数
        createCell(row, 7, sheetData.get("leaveCount").toString(), commCellStyle);
        // 旷工天数
        createCell(row, 8, sheetData.get("absentCount").toString(), commCellStyle);
        // 缺卡次数
        createCell(row, 9, sheetData.get("missCount").toString(), commCellStyle);
        // 补卡次数
        createCell(row, 10, sheetData.get("backCardCount").toString(), commCellStyle);
        // 补假次数
        createCell(row, 11, sheetData.get("backHolidayCount").toString(), commCellStyle);
        return workbook;
    }

    public static CellStyle createTitleStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static CellStyle createCommStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static void createCell(Row row, int index, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(index);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }
}
