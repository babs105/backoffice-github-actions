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
import sn.sastrans.backofficev2.trace.exportfile.IntruSearchExcelExporter;
import sn.sastrans.backofficev2.trace.exportfile.NetSearchExcelExporter;
import sn.sastrans.backofficev2.trace.mappers.IntrusionMapper;
import sn.sastrans.backofficev2.trace.models.Intrusion;
import sn.sastrans.backofficev2.trace.services.IntrusionService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@CrossOrigin(origins = "*")
@RestController

public class IntrusionController {

    @Autowired
    private IntrusionService intrusionService;

    @Autowired
    IntrusionMapper intrusionMapper;

    @GetMapping("/trace/intrusions")
    public ResponseEntity<Map<String, Object>> getAllintrusions(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<Intrusion> intrusions = new ArrayList<Intrusion>();
            Pageable paging = PageRequest.of(page, size, Sort.by("date").descending());

            Page<Intrusion> pageEvents;
            if (title == null) pageEvents = intrusionService.getAllIntrusion(paging);
            else pageEvents = intrusionService.getIntrusionByKeyword(title, paging);

            intrusions = pageEvents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("intrusions", intrusionMapper.toDto(intrusions));
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Add intrusion
    @PostMapping("/trace/intrusions")
    public ResponseEntity<IntrusionDto> addintrusion(@RequestBody @Valid IntrusionDto intrusionDto) {
        try {

            Intrusion intru = intrusionService.saveIntrusion(intrusionMapper.toEntity(intrusionDto));
            IntrusionDto netDto =  intrusionMapper.toDto(intru);

          return new ResponseEntity<>(netDto, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(balDto);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/intrusions/search")
    public ResponseEntity<Map<String, Object>> searchintrusion(@RequestBody @Valid IntruSearchRequestDto critre ) {

        try {
            Page<Intrusion> pageIntrusion= intrusionService.searchIntrusion(critre,critre.getPage(),critre.getSize());

           List<Intrusion> intrusions = pageIntrusion.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("intrusions", intrusionMapper.toDto(intrusions));
            response.put("currentPage", pageIntrusion.getNumber());
            response.put("totalItems", pageIntrusion.getTotalElements());
            response.put("totalPages", pageIntrusion.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/intrusions/search/excel")
    public void  searchintrusionExcel(@RequestBody @Valid IntruSearchExcelRequestDto critre , HttpServletResponse response) throws IOException {

        try {
            List<IntrusionDto> intrusionsdto = intrusionMapper.toDto(intrusionService.searchIntrusionExcel(critre));
            if(intrusionsdto!=null){

                IntruSearchExcelExporter excelExporter = new IntruSearchExcelExporter(intrusionsdto);
                response.setContentType("application/octet-stream");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = dateFormatter.format(new Date());
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=intrusions_" + currentDateTime + ".xlsx";
                response.setHeader(headerKey, headerValue);

                excelExporter.export(response);
            }



    } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @PutMapping("/trace/intrusions/update/{id}")
    public ResponseEntity<IntrusionDto> updateintrusion(@PathVariable Integer id, @RequestBody @Valid IntrusionDto intrusionDto) {


        Intrusion intruEntity = intrusionMapper.toEntity(intrusionDto);
        intruEntity.setId(id);
        Intrusion intruEntitySaved = intrusionService.saveIntrusion(intruEntity);

        if (intruEntitySaved != null) {
//            return new ResponseEntity<>(intrusionDto, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(intrusionDto);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trace/intrusions/{id}")
    public ResponseEntity<IntrusionDto> editintrusion(@PathVariable Integer id) {
        try {
            IntrusionDto intruDto = intrusionMapper.toDto(intrusionService.getIntrusionById(id));

//            return new ResponseEntity<>(romDto, HttpStatus.OK);
            return ResponseEntity.ok(intruDto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/trace/intrusions/delete/{id}")
    public ResponseEntity<HttpStatus> deleteintrusion(@PathVariable Integer id) {
        try {
            intrusionService.deleteIntrusion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
