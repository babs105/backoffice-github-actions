package sn.sastrans.backofficev2.report.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sn.sastrans.backofficev2.report.dtos.EvenementReportDto;
import sn.sastrans.backofficev2.report.dtos.EventReportExcelRequest;
import sn.sastrans.backofficev2.report.dtos.EventReportRequest;
import sn.sastrans.backofficev2.report.exportfile.EvenementExcelExporter;
import sn.sastrans.backofficev2.report.mappers.EvenementReportMapper;
import sn.sastrans.backofficev2.report.services.EvenementReportService;
import sn.sastrans.backofficev2.trace.models.Evenement;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class EvenementReportController {

    @Autowired
    private EvenementReportService evenementReportService;
    @Autowired
    private EvenementReportMapper evenementReportMapper;

    @PostMapping("report/trace/evenements/search")
    public ResponseEntity<Map<String, Object>> searchEvementReport(@RequestBody @Valid EventReportRequest critre ) {

        try {
            Page<Evenement> pageEventReport= evenementReportService.searchEventReport(critre,critre.getPage(),critre.getSize());

            List<Evenement> evenementReports = pageEventReport.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("evenementReports", evenementReportMapper.toDto(evenementReports));
            response.put("currentPage", pageEventReport.getNumber());
            response.put("totalItems", pageEventReport.getTotalElements());
            response.put("totalPages", pageEventReport.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/report/trace/evenements/export/excel")
    public void exportToExcel(@RequestBody @Valid EventReportExcelRequest critre , HttpServletResponse response) throws IOException {


        List<EvenementReportDto> listEvenements =evenementReportMapper.toDto(evenementReportService.searchEventExcel(critre));

        if(listEvenements!=null){
            EvenementExcelExporter excelExporter = new EvenementExcelExporter(listEvenements);

            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=Evenements_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);

            excelExporter.export(response);
        }


    }
}
