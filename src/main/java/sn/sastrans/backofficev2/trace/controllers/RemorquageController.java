package sn.sastrans.backofficev2.trace.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.sastrans.backofficev2.trace.dto.RemorquageDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.exportfile.RomSearchExcelExporter;
import sn.sastrans.backofficev2.trace.mappers.RemorquageMapper;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;
import sn.sastrans.backofficev2.trace.services.EvenementService;
import sn.sastrans.backofficev2.trace.services.RemorquageService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
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

public class RemorquageController {

    @Autowired
    private RemorquageService remorquageService;
    @Autowired
    private EvenementService evenementService;
    @Autowired
    RemorquageMapper remorquageMapper;

    //Add Remorquage
    @PostMapping("/trace/remorquages")
    public ResponseEntity<RemorquageDto> addRemorquage(@RequestBody @Valid RemorquageDto remorquageDto) {
        try {
            Remorquage rom = remorquageService.saveRemorquage(remorquageMapper.toEntity(remorquageDto));
            RemorquageDto romDto =  remorquageMapper.toDto(rom);
            Evenement evenement = evenementService.getEvenementById(remorquageDto.getEventid());
            evenement.setStatutEvent(remorquageDto.getStatutRom());
            evenement.setStatutRomEvent(remorquageDto.getStatutRom());
            evenementService.saveEvenement(evenement);

//            return new ResponseEntity<>(romDto, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.CREATED).body(romDto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/remorquages/search")
    public ResponseEntity<Map<String, Object>> searchRemorquage(@RequestBody @Valid RomSearchRequestDto critre ) {

        try {
            Page<Remorquage> pageRemorquage= remorquageService.searchRemorquage(critre,critre.getPage(),critre.getSize());

           List<Remorquage> remorquages = pageRemorquage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("remorquages", remorquageMapper.toDto(remorquages));
            response.put("currentPage", pageRemorquage.getNumber());
            response.put("totalItems", pageRemorquage.getTotalElements());
            response.put("totalPages", pageRemorquage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/remorquages/search/excel")
    public void  searchRemorquageExcel(@RequestBody @Valid RomSearchExcelRequestDto critre , HttpServletResponse response) throws IOException {

        try {
            List<RemorquageDto> remorquagesdto = remorquageMapper.toDto(remorquageService.searchRemorquageExcel(critre));
            if(remorquagesdto!=null){

                RomSearchExcelExporter excelExporter = new RomSearchExcelExporter(remorquagesdto);
                response.setContentType("application/octet-stream");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = dateFormatter.format(new Date());
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=remorquages_" + currentDateTime + ".xlsx";
                response.setHeader(headerKey, headerValue);

                excelExporter.export(response);
            }



    } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Créer un nouveau fichier Excel
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Rapport");
//
//        // Ajouter des en-têtes de colonne
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Nom");
//        headerRow.createCell(1).setCellValue("Âge");
//        headerRow.createCell(2).setCellValue("Adresse");
//
//        // Ajouter des données
//        Row dataRow = sheet.createRow(1);
//        dataRow.createCell(0).setCellValue("John Doe");
//        dataRow.createCell(1).setCellValue(30);
//        dataRow.createCell(2).setCellValue("123 Main St, Anytown USA");
//
//        // Écrire les données dans le fichier Excel
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        workbook.write(outputStream);
//
//        // Retourner le fichier Excel en tant que réponse HTTP
//        byte[] bytes = outputStream.toByteArray();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "rapport.xlsx");
//        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);


//


    }
    @PutMapping("/trace/remorquages/update/{id}")
    public ResponseEntity<RemorquageDto> updateDetailAccident(@PathVariable Integer id, @RequestBody @Valid RemorquageDto remorquageDto) {


        Remorquage romEntity = remorquageMapper.toEntity(remorquageDto);
        romEntity.setId(id);
        Remorquage romEntitySaved = remorquageService.saveRemorquage(romEntity);

        if (romEntitySaved != null) {
//            return new ResponseEntity<>(remorquageDto, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(remorquageDto);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trace/remorquages/{id}")
    public ResponseEntity<RemorquageDto> editRemorquage(@PathVariable Integer id) {
        try {
            RemorquageDto romDto = remorquageMapper.toDto(remorquageService.getRemorquageById(id));

//            return new ResponseEntity<>(romDto, HttpStatus.OK);
            return ResponseEntity.ok(romDto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/trace/remorquages/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRemorquage(@PathVariable Integer id) {
        try {
            remorquageService.deleteRemorquage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
