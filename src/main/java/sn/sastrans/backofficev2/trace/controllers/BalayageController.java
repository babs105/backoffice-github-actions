package sn.sastrans.backofficev2.trace.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.sastrans.backofficev2.trace.dto.*;
import sn.sastrans.backofficev2.trace.exportfile.BalSearchExcelExporter;
import sn.sastrans.backofficev2.trace.mappers.BalayageMapper;
import sn.sastrans.backofficev2.trace.models.Balayage;
import sn.sastrans.backofficev2.trace.services.BalayageService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@CrossOrigin(origins = "*")
@RestController

public class BalayageController {

    @Autowired
    private BalayageService balayageService;

    @Autowired
    BalayageMapper balayageMapper;
    @GetMapping("/trace/balayages")
    public ResponseEntity<Map<String, Object>> getAllbalayages(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<Balayage> balayages = new ArrayList<Balayage>();
            Pageable paging = PageRequest.of(page, size, Sort.by("date","heureDebutBalayage").descending());

            Page<Balayage> pageEvents;
            if (title == null) pageEvents = balayageService.getAllBalayage(paging);
            else pageEvents = balayageService.getBalayageByKeyword(title, paging);

            balayages = pageEvents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("balayages", balayageMapper.toDto(balayages));
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Add Balayage
    @PostMapping("/trace/balayages")
    public ResponseEntity<BalayageDto> addBalayage(@RequestBody @Valid BalayageDto balayageDto) {
        try {

            Balayage bal = balayageService.saveBalayage(balayageMapper.toEntity(balayageDto));
            BalayageDto balDto =  balayageMapper.toDto(bal);

          return new ResponseEntity<>(balDto, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(balDto);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/balayages/search")
    public ResponseEntity<Map<String, Object>> searchBalayage(@RequestBody @Valid BalSearchRequestDto critre ) {

        try {
            Page<Balayage> pageBalayage= balayageService.searchBalayage(critre,critre.getPage(),critre.getSize());

           List<Balayage> Balayages = pageBalayage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("balayages", balayageMapper.toDto(Balayages));
            response.put("currentPage", pageBalayage.getNumber());
            response.put("totalItems", pageBalayage.getTotalElements());
            response.put("totalPages", pageBalayage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/balayages/search/excel")
    public void  searchBalayageExcel(@RequestBody @Valid BalSearchExcelRequestDto critre , HttpServletResponse response) throws IOException {

        try {
            List<BalayageDto> balayagesdto = balayageMapper.toDto(balayageService.searchBalayageExcel(critre));
            if(balayagesdto!=null){

                BalSearchExcelExporter excelExporter = new BalSearchExcelExporter(balayagesdto);
                response.setContentType("application/octet-stream");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = dateFormatter.format(new Date());
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=Balayages_" + currentDateTime + ".xlsx";
                response.setHeader(headerKey, headerValue);

                excelExporter.export(response);
            }



    } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @PutMapping("/trace/balayages/update/{id}")
    public ResponseEntity<BalayageDto> updateBalayage(@PathVariable Integer id, @RequestBody @Valid BalayageDto balayageDto) {


        Balayage balEntity = balayageMapper.toEntity(balayageDto);
        balEntity.setId(id);
        Balayage balEntitySaved = balayageService.saveBalayage(balEntity);

        if (balEntitySaved != null) {
//            return new ResponseEntity<>(BalayageDto, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(balayageDto);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trace/balayages/{id}")
    public ResponseEntity<BalayageDto> editBalayage(@PathVariable Integer id) {
        try {
            BalayageDto romDto = balayageMapper.toDto(balayageService.getBalayageById(id));

//            return new ResponseEntity<>(romDto, HttpStatus.OK);
            return ResponseEntity.ok(romDto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/trace/balayages/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBalayage(@PathVariable Integer id) {
        try {
            balayageService.deleteBalayage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
