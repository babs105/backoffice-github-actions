package sn.sastrans.backofficev2.trace.exportfile;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sn.sastrans.backofficev2.trace.dto.RemorquageDto;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RomSearchExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<RemorquageDto> remorquagesDto;

    public RomSearchExcelExporter(List<RemorquageDto> remorquagesDto) {
        this.remorquagesDto = remorquagesDto;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Remorquages");

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

        createCell(row, 0, "Remorquage ID", style);
        createCell(row, 1, "Date", style);
        createCell(row, 2, "H.Debut Evenement", style);
        createCell(row, 3, "Nature Evenement", style);
        createCell(row, 4, "Cause Evenement", style);
        createCell(row, 5, "Matr.Vehicule", style);
        createCell(row, 6, "Categorie Vehicule", style);
        createCell(row, 7, "Localisation", style);
        createCell(row, 8, "H.Appel Depanneur", style);
        createCell(row, 9, "Nom Remorqueur", style);
        createCell(row, 10, "Mat.Depanneur", style);
        createCell(row, 11, "H.Depart", style);
        createCell(row, 12, "H.Arrivee", style);
        createCell(row, 13, "H.Fin Intervention", style);
        createCell(row, 14, "Durée Intervention", style);
        createCell(row, 15, "Lieu Depart", style);
        createCell(row, 16, "Lieu Depot", style);


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

        for (RemorquageDto remorquage : remorquagesDto) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, remorquage.getId(), style);
            createCell(row, columnCount++, remorquage.getDateRom(), style);
            createCell(row, columnCount++, remorquage.getHeureDebutEvent(), style);
            createCell(row, columnCount++, remorquage.getNatureEvent(), style);
            createCell(row, columnCount++, remorquage.getCauseEvent(), style);
            createCell(row, columnCount++, remorquage.getMatVhlRemorque(), style);
            createCell(row, columnCount++, remorquage.getCatVhlRemorque(), style);
            createCell(row, columnCount++, remorquage.getLocalisation(), style);
            createCell(row, columnCount++, remorquage.getHeureAppelROM(), style);
            createCell(row, columnCount++, remorquage.getNomROM(), style);
            createCell(row, columnCount++, remorquage.getMatriculeDep(), style);
            createCell(row, columnCount++, remorquage.getHeureDepartROM(), style);
            createCell(row, columnCount++, remorquage.getHeureArriveeROM(), style);
            createCell(row, columnCount++, remorquage.getHeureRomorque(), style);
            createCell(row, columnCount++, remorquage.getDureeIntervention(), style);
            createCell(row, columnCount++, remorquage.getLieuDepart(), style);
            createCell(row, columnCount++, remorquage.getLieuDepot(), style);

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
