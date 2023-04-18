package sn.sastrans.backofficev2.carburant.controllers;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sastrans.backofficev2.carburant.dto.RavitaillementDto;
import sn.sastrans.backofficev2.carburant.mappers.RavitaillementMapper;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.carburant.models.Ravitaillement;
import sn.sastrans.backofficev2.carburant.services.CuveService;
import sn.sastrans.backofficev2.carburant.services.RavitaillementService;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController

public class RavitaillementController {

    @Autowired
    private RavitaillementService ravitaillementService;


    @Autowired
    private CuveService cuveService;

    @Autowired
    RavitaillementMapper ravitaillementMapper;


    @GetMapping("/carburant/ravitaillements")
    public ResponseEntity<Map<String, Object>> getAllRavitaillements(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<Ravitaillement> ravitaillements = new ArrayList<Ravitaillement>();
            Pageable paging = PageRequest.of(page, size, Sort.by("date").descending());

            Page<Ravitaillement> pageEvents;
            if (title == null) pageEvents = ravitaillementService.getAllRavitaillement(paging);
            else pageEvents = ravitaillementService.getRavitaillementByKeyword(title, paging);

            ravitaillements = pageEvents.getContent();
            log.info("list",ravitaillements);

            Map<String, Object> response = new HashMap<>();
            response.put("ravitaillements", ravitaillementMapper.toDto(ravitaillements));
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //    Add ravitaillement
    @PostMapping("/carburant/ravitaillements")
    public ResponseEntity<RavitaillementDto> addravitaillement(@RequestBody @Valid RavitaillementDto ravitaillementDto) {
        try {
            RavitaillementDto eventDto = null;
            if(ravitaillementDto.getType().equals("cuve")){
                Cuve cuve = cuveService.getCuveByName(ravitaillementDto.getCuveid());
                cuve.setQuantity(cuve.getQuantity() - ravitaillementDto.getQuantity());
                cuve = cuveService.saveCuve(cuve);
                
                if(cuve != null){
//                    return   ravitaillementRepository.save(ravitaillement);
             eventDto = ravitaillementMapper.toDto(ravitaillementService.saveRavitaillement(ravitaillementMapper.toEntity(ravitaillementDto)));    
                }

            }else{
                ravitaillementDto.setCuveid(null);
              eventDto = ravitaillementMapper.toDto(ravitaillementService.saveRavitaillement(ravitaillementMapper.toEntity(ravitaillementDto)));
            }
          
//            ravitaillement event = ravitaillementService.saveravitaillement(ravitaillementMapper.toEntity(ravitaillementDto));
            
            return new ResponseEntity<>(eventDto, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/carburant/ravitaillements/update/{id}")
    public ResponseEntity<RavitaillementDto> updateravitaillement(@PathVariable Integer id, @RequestBody @Valid RavitaillementDto ravitaillementDto) {
//        log.info("DED" + ravitaillementDto.getId());

        Ravitaillement ravi = ravitaillementMapper.toEntity(ravitaillementDto);
        ravi.setId(id);
        RavitaillementDto raviDto = ravitaillementMapper.toDto(ravitaillementService.saveRavitaillement(ravi));
        if (raviDto != null) {
            return new ResponseEntity<>(raviDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/carburant/ravitaillements/{id}")
    public ResponseEntity<RavitaillementDto> editravitaillement(@PathVariable Integer id) {
//        ravitaillement ravitaillementdto = ravitaillementService.getravitaillementById(id);
        RavitaillementDto ravitaillementdto = ravitaillementMapper.toDto(ravitaillementService.getRavitaillementById(id));
        if (ravitaillementdto != null) {
            return new ResponseEntity<>(ravitaillementdto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete ravitaillement by id
    @DeleteMapping("/carburant/ravitaillements/delete/{id}")
    public ResponseEntity<HttpStatus> deleteravitaillement(@PathVariable Integer id) {
        try {
            ravitaillementService.deleteRavitaillement(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
