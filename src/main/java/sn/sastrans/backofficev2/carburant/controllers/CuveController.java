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

    }

    @GetMapping("/carburant/cuve/{id}")
    public ResponseEntity<CuveDto>  getById(@PathVariable Integer id) {
         CuveDto  cuveDto = cuveMapper.toDto(cuveService.getCuveById(id));
         return new ResponseEntity<>(cuveDto, HttpStatus.OK);
    }


    @PostMapping("/carburant/cuves")
    public ResponseEntity<CuveDto> addCuve(@RequestBody @Valid CuveDto cuveDto) {

            Cuve cuveSaved = cuveService.saveCuve(cuveMapper.toEntity(cuveDto));
            CuveDto cuve = cuveMapper.toDto(cuveSaved);
            return new ResponseEntity<>(cuve, HttpStatus.OK);

    }

    @PutMapping("/carburant/cuve/update/{id}")
    public ResponseEntity<CuveDto> updateCuve(@PathVariable Integer id, @RequestBody @Valid CuveDto cuveDto) {

            Cuve cuveEntity = cuveMapper.toEntity(cuveDto);
            cuveEntity.setId(id);
            Cuve cuveSaved = cuveService.saveCuve(cuveEntity);
            return new ResponseEntity<>(cuveDto, HttpStatus.OK);

    }


    @DeleteMapping("/carburant/cuve/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCuve(@PathVariable Integer id) {
            cuveService.deleteCuve(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

   
}
