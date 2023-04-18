package sn.sastrans.backofficev2.parameters.controllers;



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
import sn.sastrans.backofficev2.carburant.mappers.CuveMapper;
import sn.sastrans.backofficev2.parameters.dto.VehicleAffectationDto;
import sn.sastrans.backofficev2.parameters.mappers.VehicleAffectationMapper;
import sn.sastrans.backofficev2.parameters.models.VehicleAffectation;
import sn.sastrans.backofficev2.parameters.services.VehicleAffectationService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@CrossOrigin(origins = "*")
@RestController

public class VehicleAffectationController {
    @Autowired
    private VehicleAffectationService vehicleAffectationService;
    @Autowired
    private VehicleAffectationMapper vehicleAffectationMapper;

    @GetMapping("/paramvhl/affectations")
    public ResponseEntity<Map<String, Object>> getAllaffectations(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<VehicleAffectation> affectations = new ArrayList<VehicleAffectation>();
            Pageable paging = PageRequest.of(page, size, Sort.by("id").descending());

            Page<VehicleAffectation> pageaffectations;
            if (title == null) pageaffectations = vehicleAffectationService.getAllVehicleAffection(paging);
            else pageaffectations = vehicleAffectationService.getAllVehicleAffectationByKeyword(title, paging);

            affectations = pageaffectations.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("affectations", vehicleAffectationMapper.toDto(affectations));
            response.put("currentPage", pageaffectations.getNumber());
            response.put("totalItems", pageaffectations.getTotalElements());
            response.put("totalPages", pageaffectations.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/paramvhl/affectations/all")
    public ResponseEntity<List<VehicleAffectationDto>> getAllAffectations() {

        try {
            List<VehicleAffectationDto> affectations =vehicleAffectationMapper.toDto(vehicleAffectationService.getAllVehicleAffection());


            return new ResponseEntity<>(affectations, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paramvhl/affectation/{id}")
    public VehicleAffectation getById(@PathVariable Integer id) {
        return vehicleAffectationService.getVehicleAffectionById(id);
    }


    @PostMapping("/paramvhl/affectations")
    public ResponseEntity<VehicleAffectationDto> addaffectation(@RequestBody @Valid VehicleAffectationDto affectationDto) {

        try {
            VehicleAffectation affectationSaved = vehicleAffectationService.saveVehicleAffection(vehicleAffectationMapper.toEntity(affectationDto));
            VehicleAffectationDto vhlaffectationDto = vehicleAffectationMapper.toDto(affectationSaved);
            return new ResponseEntity<>(vhlaffectationDto, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/paramvhl/affectation/update/{id}")
    public ResponseEntity<VehicleAffectationDto> updateaffectation(@PathVariable Integer id, @RequestBody @Valid VehicleAffectationDto affectationDto) {

        try {
            VehicleAffectation affectationEntity = vehicleAffectationMapper.toEntity(affectationDto);
            affectationEntity.setId(id);
            VehicleAffectation affectationSaved = vehicleAffectationService.saveVehicleAffection(affectationEntity);

            if (affectationSaved != null) {
                return new ResponseEntity<>(affectationDto, HttpStatus.OK);
//                return ResponseEntity.status(HttpStatus.ACCEPTED).body(affectationDto);
            } else return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/paramvhl/affectation/delete/{id}")
    public ResponseEntity<HttpStatus> deleteaffectation(@PathVariable Integer id) {
        try {
            vehicleAffectationService.deleteVehicleAffection(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}