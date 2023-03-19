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
import sn.sastrans.backofficev2.carburant.dto.CuveDto;
import sn.sastrans.backofficev2.carburant.mappers.CuveMapper;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.carburant.services.CuveService;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@CrossOrigin(origins = "*")
@RestController 
public class CuveController {

    @Autowired
    private CuveService cuveService;
    @Autowired
    private CuveMapper cuveMapper;

    @GetMapping("/carburant/cuves")
    public ResponseEntity<Map<String, Object>> getAllCuves(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<Cuve> cuves = new ArrayList<Cuve>();
            Pageable paging = PageRequest.of(page, size, Sort.by("id").descending());

            Page<Cuve> pagecuves;
            if (title == null) pagecuves = cuveService.getAllCuve(paging);
            else pagecuves = cuveService.getAllCuveByKeyword(title, paging);

            cuves = pagecuves.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("cuves", cuveMapper.toDto(cuves));
            response.put("currentPage", pagecuves.getNumber());
            response.put("totalItems", pagecuves.getTotalElements());
            response.put("totalPages", pagecuves.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/carburant/cuve/{id}")
    public Cuve getById(@PathVariable Integer id) {
        return cuveService.getCuveById(id);
    }


    @PostMapping("/carburant/cuves")
    public ResponseEntity<CuveDto> addCuve(@RequestBody @Valid CuveDto cuveDto) {

        try {
            Cuve cuveSaved = cuveService.saveCuve(cuveMapper.toEntity(cuveDto));
            CuveDto cuve = cuveMapper.toDto(cuveSaved);
            return new ResponseEntity<>(cuve, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/carburant/cuve/update/{id}")
    public ResponseEntity<CuveDto> updateCuve(@PathVariable Integer id, @RequestBody @Valid CuveDto cuveDto) {

        try {
            Cuve cuveEntity = cuveMapper.toEntity(cuveDto);
            cuveEntity.setId(id);
            Cuve cuveSaved = cuveService.saveCuve(cuveEntity);

            if (cuveSaved != null) {
                return new ResponseEntity<>(cuveDto, HttpStatus.OK);
//                return ResponseEntity.status(HttpStatus.ACCEPTED).body(cuveDto);
            }
            else  return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/carburant/cuve/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCuve(@PathVariable Integer id) {
        try {
            cuveService.deleteCuve(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   
}
