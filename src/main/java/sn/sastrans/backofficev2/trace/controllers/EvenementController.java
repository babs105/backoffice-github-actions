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
import sn.sastrans.backofficev2.trace.dto.EvenementDto;
import sn.sastrans.backofficev2.trace.dto.EventSearchDTO;
import sn.sastrans.backofficev2.trace.mappers.EvenementMapper;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.services.DetailAccidentService;
import sn.sastrans.backofficev2.trace.services.EvenementService;
import sn.sastrans.backofficev2.trace.services.RemorquageService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class EvenementController {

    @Autowired
    private EvenementService evenementService;
    @Autowired
    private DetailAccidentService detailAccidentService;

    @Autowired
    private RemorquageService remorquageService;

    @Autowired
    EvenementMapper evenementMapper;


    @GetMapping("/trace/evenements")
    public ResponseEntity<Map<String, Object>> getAllEvenements(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<Evenement> evenements = new ArrayList<Evenement>();
            Pageable paging = PageRequest.of(page, size, Sort.by("dateEvent","heureDebutEvent").descending());

            Page<Evenement> pageEvents;
            if (title == null) pageEvents = evenementService.getAllEvenement(paging);
            else pageEvents = evenementService.getAllEvenementByKeyword(title, paging);

            evenements = pageEvents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("evenements", evenementMapper.toDto(evenements));
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // show form add
//    @GetMapping("/trace/evenementAdd")
//    public String showFormAddEvenement(Model model){
//        return "trace/evenementAdd";
//    }

    @PostMapping("/trace/search")
    public ResponseEntity<List<Evenement>> searchEvenement(@RequestBody EventSearchDTO critere) {
        try {
            return new ResponseEntity<>(evenementService.searchEvenement(critere), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    Add Evenement
    @PostMapping("/trace/evenements")
    public ResponseEntity<EvenementDto> addEvenement(@RequestBody @Valid EvenementDto evenementDto) {
        try {
            EvenementDto eventDto = evenementMapper.toDto(evenementService.saveEvenement(evenementMapper.toEntity(evenementDto)));
//            Evenement event = evenementService.saveEvenement(evenementMapper.toEntity(evenementDto));
            return new ResponseEntity<>(eventDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/trace/evenements/update/{id}")
    public ResponseEntity<EvenementDto> updateEvenement(@PathVariable Integer id, @RequestBody @Valid EvenementDto evenementDto) {
//        log.info("DED" + evenementDto.getId());

        Evenement event = evenementMapper.toEntity(evenementDto);
        event.setId(id);
        EvenementDto evenetDto = evenementMapper.toDto(evenementService.saveEvenement(event));
        if (evenetDto != null) {
            return new ResponseEntity<>(evenetDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trace/evenements/{id}")
    public ResponseEntity<EvenementDto> editEvenement(@PathVariable Integer id) {
//        Evenement evenementdto = evenementService.getEvenementById(id);
       EvenementDto evenementdto = evenementMapper.toDto(evenementService.getEvenementById(id));
        if (evenementdto != null) {
            return new ResponseEntity<>(evenementdto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete Evenement by id
    @DeleteMapping("/trace/evenements/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEvenement(@PathVariable Integer id) {
        try {
            evenementService.deleteEvenement(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
//    @GetMapping("/trace/evenement/{eventId}/deleteEvent/{romId}")
//    public String deleteRomInEvenement(@PathVariable Integer eventId,@PathVariable Integer romId){
//        remorquageService.deleteRemorquage(romId);
//        return "redirect:/trace/evenement/Edit/"+eventId;
//    }
//    @GetMapping("/trace/evenement/{eventId}/deleteAccident/{accidentId}")
//    public String deleteAcciInEvenement(@PathVariable Integer eventId,@PathVariable Integer accidentId){
//        detailAccidentService.deleteDetailAccident(accidentId);
//        return "redirect:/trace/evenement/Edit/"+eventId;
//    }
}
