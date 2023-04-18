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
import sn.sastrans.backofficev2.trace.exportfile.NetSearchExcelExporter;
import sn.sastrans.backofficev2.trace.mappers.NettoyageMapper;
import sn.sastrans.backofficev2.trace.models.Nettoyage;
import sn.sastrans.backofficev2.trace.services.NettoyageService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@CrossOrigin(origins = "*")
@RestController

public class NettoyageController {

    @Autowired
    private NettoyageService nettoyageService;

    @Autowired
    NettoyageMapper nettoyageMapper;

    @GetMapping("/trace/nettoyages")
    public ResponseEntity<Map<String, Object>> getAllnettoyages(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<Nettoyage> nettoyages = new ArrayList<Nettoyage>();
            Pageable paging = PageRequest.of(page, size, Sort.by("date").descending());

            Page<Nettoyage> pageEvents;
            if (title == null) pageEvents = nettoyageService.getAllNettoyage(paging);
            else pageEvents = nettoyageService.getNettoyageByKeyword(title, paging);

            nettoyages = pageEvents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("nettoyages", nettoyageMapper.toDto(nettoyages));
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Add nettoyage
    @PostMapping("/trace/nettoyages")
    public ResponseEntity<NettoyageDto> addnettoyage(@RequestBody @Valid NettoyageDto nettoyageDto) {
        try {

            Nettoyage net = nettoyageService.saveNettoyage(nettoyageMapper.toEntity(nettoyageDto));
            NettoyageDto netDto =  nettoyageMapper.toDto(net);

          return new ResponseEntity<>(netDto, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(balDto);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/nettoyages/search")
    public ResponseEntity<Map<String, Object>> searchnettoyage(@RequestBody @Valid NetSearchRequestDto critre ) {

        try {
            Page<Nettoyage> pagenettoyage= nettoyageService.searchNettoyage(critre,critre.getPage(),critre.getSize());

           List<Nettoyage> nettoyages = pagenettoyage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("nettoyages", nettoyageMapper.toDto(nettoyages));
            response.put("currentPage", pagenettoyage.getNumber());
            response.put("totalItems", pagenettoyage.getTotalElements());
            response.put("totalPages", pagenettoyage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/nettoyages/search/excel")
    public void  searchnettoyageExcel(@RequestBody @Valid NetSearchExcelRequestDto critre , HttpServletResponse response) throws IOException {

        try {
            List<NettoyageDto> nettoyagesdto = nettoyageMapper.toDto(nettoyageService.searchNettoyageExcel(critre));
            if(nettoyagesdto!=null){

                NetSearchExcelExporter excelExporter = new NetSearchExcelExporter(nettoyagesdto);
                response.setContentType("application/octet-stream");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = dateFormatter.format(new Date());
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=nettoyages_" + currentDateTime + ".xlsx";
                response.setHeader(headerKey, headerValue);

                excelExporter.export(response);
            }



    } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @PutMapping("/trace/nettoyages/update/{id}")
    public ResponseEntity<NettoyageDto> updatenettoyage(@PathVariable Integer id, @RequestBody @Valid NettoyageDto nettoyageDto) {


        Nettoyage netEntity = nettoyageMapper.toEntity(nettoyageDto);
        netEntity.setId(id);
        Nettoyage netEntitySaved = nettoyageService.saveNettoyage(netEntity);

        if (netEntitySaved != null) {
//            return new ResponseEntity<>(nettoyageDto, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(nettoyageDto);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trace/nettoyages/{id}")
    public ResponseEntity<NettoyageDto> editnettoyage(@PathVariable Integer id) {
        try {
            NettoyageDto netDto = nettoyageMapper.toDto(nettoyageService.getNettoyageById(id));

//            return new ResponseEntity<>(romDto, HttpStatus.OK);
            return ResponseEntity.ok(netDto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/trace/nettoyages/delete/{id}")
    public ResponseEntity<HttpStatus> deletenettoyage(@PathVariable Integer id) {
        try {
            nettoyageService.deleteNettoyage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
