package sn.sastrans.backofficev2.trace.exportfile;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sn.sastrans.backofficev2.trace.dto.BalayageDto;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BalSearchExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<BalayageDto> balayagesDto;

    public BalSearchExcelExporter(List<BalayageDto> balayagesDto) {
        this.balayagesDto =balayagesDto;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Balayages");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        // fill foreground color ...
        style.setFillForegroundColor(IndexedColors.GOLD.index);
        // and solid fill pattern produces solid grey cell fill
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        createCell(row, 0, "Balayage ID", style);
        createCell(row, 1, "Date", style);
        createCell(row, 2, "Type Balayage", style);
        createCell(row, 3, "Type Balisage", style);
        createCell(row, 4, "Date/Heure Pose Balise", style);
        createCell(row, 5, "Date/Heure Depose Balise", style);
        createCell(row, 6, "Pk Debut Balisage", style);
        createCell(row, 7, "Pk Fin Balisage", style);
        createCell(row, 8, "H.debut Balayage", style);
        createCell(row, 9, "H.fin Balayage", style);
        createCell(row, 10, "Pk Debut Balayage", style);
        createCell(row, 11, "Pk Fin Balayage", style);
        createCell(row, 12, "Localisation", style);



    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Date) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            cell.setCellValue(df.format(value));
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (BalayageDto balayage : balayagesDto) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, balayage.getId(), style);
            createCell(row, columnCount++, balayage.getDate(), style);
            createCell(row, columnCount++, balayage.getTypeBalayage(), style);
            createCell(row, columnCount++, balayage.getTypeBalisage(), style);
            createCell(row, columnCount++, balayage.getDatePose(), style);
            createCell(row, columnCount++, balayage.getDateDepose(), style);
            createCell(row, columnCount++, balayage.getPkdebutBalise(), style);
            createCell(row, columnCount++, balayage.getPkfinBalise(), style);
            createCell(row, columnCount++, balayage.getHeureDebutBalayage(), style);
            createCell(row, columnCount++, balayage.getHeureFinBalayage(), style);
            createCell(row, columnCount++, balayage.getPkdebutBalayage(), style);
            createCell(row, columnCount++, balayage.getPkfinBalayage(), style);
            createCell(row, columnCount++, balayage.getLocalisation(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
