package sn.sastrans.backofficev2.trace.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.sastrans.backofficev2.trace.dto.AcciSearchDto;
import sn.sastrans.backofficev2.trace.dto.DetailAccidentDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchDto;
import sn.sastrans.backofficev2.trace.mappers.DetailAccidentMapper;
import sn.sastrans.backofficev2.trace.models.DetailAccident;
import sn.sastrans.backofficev2.trace.models.Remorquage;
import sn.sastrans.backofficev2.trace.services.DetailAccidentService;
import sn.sastrans.backofficev2.trace.services.EvenementService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class DetailAccidentController {

    @Autowired
    private DetailAccidentService detailAccidentService;
    @Autowired
    private EvenementService evenementService;

    @Autowired
    DetailAccidentMapper detailAccidentMapper;


    //Add DetailAccident
    @PostMapping("/trace/detailAccidents")
    public ResponseEntity<DetailAccidentDto> addDetailAccident(@RequestBody @Valid DetailAccidentDto detailAccidentDto) {
        try {
            DetailAccident detailAccident = detailAccidentService.saveDetailAccident(detailAccidentMapper.toEntity(detailAccidentDto));
            DetailAccidentDto detailDto = detailAccidentMapper.toDto(detailAccident);
            return new ResponseEntity<>(detailDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trace/detailAccidents/{id}")
    public ResponseEntity<DetailAccidentDto> editDetailAccident(@PathVariable Integer id) {
        try {
            DetailAccidentDto accidentDto = detailAccidentMapper.toDto(detailAccidentService.getDetailAccidentById(id));

            return new ResponseEntity<>(accidentDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/trace/detailAccidents/update/{id}")
    public ResponseEntity<DetailAccidentDto> updateDetailAccident(@PathVariable Integer id, @RequestBody @Valid DetailAccidentDto detailAccidentDto) {

//         DetailAccident accidentToUpdate= detailAccidentService.getDetailAccidentById(detailAccidentDto.getId());
        DetailAccident detailAccidentEntity = detailAccidentMapper.toEntity(detailAccidentDto);
        detailAccidentEntity.setId(id);
        DetailAccident acciEntitySaved = detailAccidentService.saveDetailAccident(detailAccidentEntity);

        if (acciEntitySaved != null) {
            return new ResponseEntity<>(detailAccidentDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //// delete DetailAccident by id
    @DeleteMapping("/trace/detailAccidents/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDetailAccident(@PathVariable Integer id) {
        try {
            detailAccidentService.deleteDetailAccident(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/trace/detailAccidents/search")
    public ResponseEntity<Map<String, Object>> searcDetailAccident(@RequestBody @Valid AcciSearchDto critere ) {

        try {
            Page<DetailAccident> pageDetailAccident= detailAccidentService.searchDetailAccident(critere,critere.getPage(),critere.getSize());

            List<DetailAccident> detailAccidents = pageDetailAccident.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("detailAccidents", detailAccidentMapper.toDto(detailAccidents));
            response.put("currentPage", pageDetailAccident.getNumber());
            response.put("totalItems", pageDetailAccident.getTotalElements());
            response.put("totalPages", pageDetailAccident.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
