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
import sn.sastrans.backofficev2.trace.exportfile.DeshSearchExcelExporter;
import sn.sastrans.backofficev2.trace.mappers.DesherbageMapper;
import sn.sastrans.backofficev2.trace.models.Desherbage;
import sn.sastrans.backofficev2.trace.services.DesherbageService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class DesherbageController {

    @Autowired
    private DesherbageService desherbageService;

    @Autowired
    DesherbageMapper desherbageMapper;

    @GetMapping("/trace/desherbages")
    public ResponseEntity<Map<String, Object>> getAlldesherbages(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<Desherbage> desherbages = new ArrayList<Desherbage>();
            Pageable paging = PageRequest.of(page, size, Sort.by("date").descending());

            Page<Desherbage> pageEvents;
            if (title == null) pageEvents = desherbageService.getAllDesherbage(paging);
            else pageEvents = desherbageService.getDesherbageByKeyword(title, paging);

            desherbages = pageEvents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("desherbages", desherbageMapper.toDto(desherbages));
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Add Desherbage
    @PostMapping("/trace/desherbages")
    public ResponseEntity<DesherbageDto> addDesherbage(@RequestBody @Valid DesherbageDto desherbageDto) {
        try {

            Desherbage intru = desherbageService.saveDesherbage(desherbageMapper.toEntity(desherbageDto));
            DesherbageDto netDto =  desherbageMapper.toDto(intru);

          return new ResponseEntity<>(netDto, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(balDto);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/desherbages/search")
    public ResponseEntity<Map<String, Object>> searchdesherbage(@RequestBody @Valid DeshSearchRequestDto critre ) {

        try {
            Page<Desherbage> pagedesherbage= desherbageService.searchDesherbage(critre,critre.getPage(),critre.getSize());

           List<Desherbage> desherbages = pagedesherbage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("desherbages", desherbageMapper.toDto(desherbages));
            response.put("currentPage", pagedesherbage.getNumber());
            response.put("totalItems", pagedesherbage.getTotalElements());
            response.put("totalPages", pagedesherbage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/desherbages/search/excel")
    public void  searchDesherbageExcel(@RequestBody @Valid DeshSearchExcelRequestDto critre , HttpServletResponse response) throws IOException {

        try {
            List<DesherbageDto> desherbagesdto = desherbageMapper.toDto(desherbageService.searchDesherbageExcel(critre));
            if(desherbagesdto!=null){

                DeshSearchExcelExporter excelExporter = new DeshSearchExcelExporter(desherbagesdto);
                response.setContentType("application/octet-stream");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = dateFormatter.format(new Date());
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=desherbages_" + currentDateTime + ".xlsx";
                response.setHeader(headerKey, headerValue);

                excelExporter.export(response);
            }



    } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @PutMapping("/trace/desherbages/update/{id}")
    public ResponseEntity<DesherbageDto> updateDesherbage(@PathVariable Integer id, @RequestBody @Valid DesherbageDto desherbageDto) {


        Desherbage intruEntity = desherbageMapper.toEntity(desherbageDto);
        intruEntity.setId(id);
        Desherbage intruEntitySaved = desherbageService.saveDesherbage(intruEntity);

        if (intruEntitySaved != null) {
//            return new ResponseEntity<>(desherbageDto, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(desherbageDto);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trace/desherbages/{id}")
    public ResponseEntity<DesherbageDto> editDesherbage(@PathVariable Integer id) {
        try {
            DesherbageDto intruDto = desherbageMapper.toDto(desherbageService.getDesherbageById(id));

//            return new ResponseEntity<>(romDto, HttpStatus.OK);
            return ResponseEntity.ok(intruDto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/trace/desherbages/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDesherbage(@PathVariable Integer id) {
        try {
            desherbageService.deleteDesherbage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
