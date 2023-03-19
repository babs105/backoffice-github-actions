package sn.sastrans.backofficev2.trace.exportfile;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sn.sastrans.backofficev2.trace.dto.NettoyageDto;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NetSearchExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<NettoyageDto> nettoyagesDto;

    public NetSearchExcelExporter(List<NettoyageDto> nettoyagesDto) {
        this.nettoyagesDto =nettoyagesDto;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Nettoyages");

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

        createCell(row, 0, "Nettoyage ID", style);
        createCell(row, 1, "Date", style);
        createCell(row, 2, "Type Nettoyage", style);
        createCell(row, 3, "Type Nettoyage", style);
        createCell(row, 4, "Date/Heure Pose Balise", style);
        createCell(row, 5, "Date/Heure Depose Balise", style);
        createCell(row, 6, "Pk Debut Nettoyage", style);
        createCell(row, 7, "Pk Fin Nettoyage", style);
        createCell(row, 8, "H.debut Nettoyage", style);
        createCell(row, 9, "H.fin Nettoyage", style);
        createCell(row, 10, "Pk Debut Nettoyage", style);
        createCell(row, 11, "Pk Fin Nettoyage", style);




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

        for (NettoyageDto Nettoyage : nettoyagesDto) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, Nettoyage.getId(), style);
            createCell(row, columnCount++, Nettoyage.getDate(), style);
            createCell(row, columnCount++, Nettoyage.getTypeNettoyage(), style);
            createCell(row, columnCount++, Nettoyage.getDatePose(), style);
            createCell(row, columnCount++, Nettoyage.getDateDepose(), style);
            createCell(row, columnCount++, Nettoyage.getPkdebutBalise(), style);
            createCell(row, columnCount++, Nettoyage.getPkfinBalise(), style);
            createCell(row, columnCount++, Nettoyage.getHeureDebut(), style);
            createCell(row, columnCount++, Nettoyage.getHeureFin(), style);
            createCell(row, columnCount++, Nettoyage.getPkdebut(), style);
            createCell(row, columnCount++, Nettoyage.getPkfin(), style);


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
