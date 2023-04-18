package sn.sastrans.backofficev2.trace.exportfile;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sn.sastrans.backofficev2.trace.dto.DesherbageDto;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DeshSearchExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<DesherbageDto> desherbagesDto;

    public DeshSearchExcelExporter(List<DesherbageDto> desherbagesDto) {
        this.desherbagesDto =desherbagesDto;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Desherbages");

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


        createCell(row, 0, "Desherbage ID", style);
        createCell(row, 1, "Date", style);
        createCell(row, 2, "H.debut", style);
        createCell(row, 3, "H.fin", style);
        createCell(row, 4, "Pk Debut ", style);
        createCell(row, 5, "Pk Fin ", style);
        createCell(row, 6, "Sens", style);
        createCell(row, 7, "Secteur", style);
        createCell(row, 8, "Localisation", style);



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

        for (DesherbageDto desherbage : desherbagesDto) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, desherbage.getId(), style);
            createCell(row, columnCount++, desherbage.getDate(), style);
            createCell(row, columnCount++, desherbage.getHeureDebut(), style);
            createCell(row, columnCount++, desherbage.getHeureFin(), style);
            createCell(row, columnCount++, desherbage.getPkDebut(), style);
            createCell(row, columnCount++, desherbage.getPkFin(), style);
            createCell(row, columnCount++, desherbage.getSens(), style);
            createCell(row, columnCount++, desherbage.getSecteur(), style);
            createCell(row, columnCount++, desherbage.getLocalisation(), style);


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
