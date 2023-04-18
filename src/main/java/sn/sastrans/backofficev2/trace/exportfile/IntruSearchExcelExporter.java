package sn.sastrans.backofficev2.trace.exportfile;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sn.sastrans.backofficev2.trace.dto.IntrusionDto;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class IntruSearchExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<IntrusionDto> intrusionsDto;

    public IntruSearchExcelExporter(List<IntrusionDto> intrusionsDto) {
        this.intrusionsDto =intrusionsDto;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Intrusions");

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

        createCell(row, 0, "Intrusion ID", style);
        createCell(row, 1, "Date", style);
        createCell(row, 2, "Type Intrusion", style);
        createCell(row, 3, "Emis Par", style);
        createCell(row, 4, "H.Annonce ", style);
        createCell(row, 5, "Localisation", style);
        createCell(row, 6, "Sens", style);
        createCell(row, 7, "Voie", style);
        createCell(row, 8, "Secteur", style);
        createCell(row, 9, "Heure Depart", style);
        createCell(row, 10, "Heure D'arrivee", style);
        createCell(row, 11, "H.Fin Intervention", style);
        createCell(row, 12, "Action Menée", style);
        createCell(row, 13, "Nombre", style);




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
        } else if (value instanceof LocalDateTime) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            cell.setCellValue(((LocalDateTime) value).format(dateTimeFormatter));
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

        for (IntrusionDto intrusion : intrusionsDto) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, intrusion.getId(), style);
            createCell(row, columnCount++, intrusion.getDate(), style);
            createCell(row, columnCount++, intrusion.getTypeIntrusion(), style);
            createCell(row, columnCount++, intrusion.getEmisPar(), style);
            createCell(row, columnCount++, intrusion.getHeureAnnonce(), style);
            createCell(row, columnCount++, intrusion.getLocalisation(), style);
            createCell(row, columnCount++, intrusion.getSens(), style);
            createCell(row, columnCount++, intrusion.getVoie(), style);
            createCell(row, columnCount++, intrusion.getSecteur(), style);
            createCell(row, columnCount++, intrusion.getHeureDepart(), style);
            createCell(row, columnCount++, intrusion.getHeureArrivee(), style);
            createCell(row, columnCount++, intrusion.getHeureFin(), style);
            createCell(row, columnCount++, intrusion.getAction(), style);
            createCell(row, columnCount++, intrusion.getNbre(), style);



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
