package sn.sastrans.backofficev2.trace.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.sastrans.backofficev2.trace.dto.DetailAccidentDto;
import sn.sastrans.backofficev2.trace.dto.RemorquageDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchDto;
import sn.sastrans.backofficev2.trace.mappers.RemorquageMapper;
import sn.sastrans.backofficev2.trace.models.DetailAccident;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;
import sn.sastrans.backofficev2.trace.services.EvenementService;
import sn.sastrans.backofficev2.trace.services.RemorquageService;

import javax.validation.Valid;
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
    public ResponseEntity<Map<String, Object>> searchRemorquage(@RequestBody @Valid RomSearchDto critre ) {

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
