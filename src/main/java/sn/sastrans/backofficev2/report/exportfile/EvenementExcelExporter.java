package sn.sastrans.backofficev2.report.exportfile;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sn.sastrans.backofficev2.report.dtos.EvenementReportDto;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class EvenementExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<EvenementReportDto> listEvenements;

    public EvenementExcelExporter(List<EvenementReportDto> listEvenements) {
        this.listEvenements = listEvenements;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Evenements");

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

        createCell(row, 0, "Evenement ID", style);
        createCell(row, 1, "Date", style);
        createCell(row, 2, "Nature", style);
        createCell(row, 3, "Cause", style);
        createCell(row, 4, "Categ.Vehicule", style);
        createCell(row, 5, "Matr.Vehicule", style);
        createCell(row, 6, "Localisation", style);
        createCell(row, 7, "Emis Par", style);
        createCell(row, 8, "H.debut Evenement", style);
        createCell(row, 9, "Heure Appel OPC", style);
        createCell(row, 10, "Heure Appel PAT", style);
        createCell(row, 11, "Patrouilleur", style);
        createCell(row, 12, "H.Arrivee PAT", style);
        createCell(row, 13, "Date/Heure Pose Balise", style);
        createCell(row, 14, "Date/Heure Depose Balise", style);
        createCell(row, 15, "Type Balisage", style);
        createCell(row, 16, "Pk Debut Balisage", style);
        createCell(row, 17, "Pk Fin Balisage", style);
        createCell(row, 18, "Statut Event", style);
        createCell(row, 19, "Observations", style);

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
            if(value!=null){
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                cell.setCellValue(((LocalDateTime) value).format(dateTimeFormatter));
            }else{

                cell.setCellValue("NON");
            }

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

        for (EvenementReportDto evenement : listEvenements) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, evenement.getId(), style);
            createCell(row, columnCount++, evenement.getDateEvent(), style);
            createCell(row, columnCount++, evenement.getNatureEvent(), style);
            createCell(row, columnCount++, evenement.getCauseEvent(), style);
            createCell(row, columnCount++, evenement.getTypeVehicule(), style);
            createCell(row, columnCount++, evenement.getMatVehicule(), style);
            createCell(row, columnCount++, evenement.getLocalisation(), style);
            createCell(row, columnCount++, evenement.getEmisPar(), style);
            createCell(row, columnCount++, evenement.getHeureDebutEvent(), style);
            createCell(row, columnCount++, evenement.getHeureAppelOPC(), style);
            createCell(row, columnCount++, evenement.getHeureAppelPAT(), style);
            createCell(row, columnCount++, evenement.getNomPAT(), style);
            createCell(row, columnCount++, evenement.getHeureArriveePAT(), style);
            createCell(row, columnCount++, evenement.getDateheurePoseBalise(), style);
            createCell(row, columnCount++, evenement.getDateheureDeposeBalise(), style);
            createCell(row, columnCount++, evenement.getTypeBalisage(), style);
            createCell(row, columnCount++, evenement.getPkDebutBalisage(), style);
            createCell(row, columnCount++, evenement.getPkFinBalisage(), style);
            createCell(row, columnCount++, evenement.getStatutEvent(), style);
            createCell(row, columnCount++, evenement.getObservation(), style);



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
