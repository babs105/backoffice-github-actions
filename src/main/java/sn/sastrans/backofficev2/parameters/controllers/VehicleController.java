package sn.sastrans.backofficev2.parameters.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sn.sastrans.backofficev2.parameters.dto.VehicleDto;
import sn.sastrans.backofficev2.parameters.mappers.VehicleMapper;
import sn.sastrans.backofficev2.parameters.models.Vehicle;
import sn.sastrans.backofficev2.parameters.services.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private VehicleAffectationService vehicleAffectationService;
    @Autowired
    private VehicleModelService vehicleModelService;
    @Autowired
    private VehicleMakeService vehicleMakeService;
    @Autowired
    private VehicleStatusService vehicleStatusService;

    @Autowired
    private VehicleCategoryService vehicleCategoryService;
    @Autowired
    private VehicleMapper vehicleMapper;

    @GetMapping("/paramvhl/vehicles")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<Vehicle> vehicles = new ArrayList<Vehicle>();
           Pageable paging = PageRequest.of(page, size,Sort.by("acquisitionDate").descending());
//            Pageable paging = PageRequest.of(page, size);
            Page<Vehicle> pageEvents;
            if (title == null) pageEvents = vehicleService.getAllVehicle(paging);
            else pageEvents = vehicleService.getAllVehicleByKeyword(title, paging);

            vehicles = pageEvents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("vehicles", vehicleMapper.toDto(vehicles));
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //    Add Vehicle
    @PostMapping("/paramvhl/vehicles")
    public ResponseEntity<VehicleDto> addVehicle(@RequestBody @Valid VehicleDto vehicleDto) {
        try {
            VehicleDto vhldto = vehicleMapper.toDto(vehicleService.saveVehicle(vehicleMapper.toEntity(vehicleDto)));
//            Vehicle event = VehicleService.saveVehicle(VehicleMapper.toEntity(VehicleDto));
            return new ResponseEntity<>(vhldto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/paramvhl/vehicles/update/{id}")
    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable Integer id, @RequestBody @Valid VehicleDto vehicleDto) {
//        log.info("DED" + VehicleDto.getId());

        Vehicle vhl = vehicleMapper.toEntity(vehicleDto);
        vhl.setId(id);
        VehicleDto raviDto = vehicleMapper.toDto(vehicleService.saveVehicle(vhl));
        if (raviDto != null) {
            return new ResponseEntity<>(raviDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paramvhl/vehicles/{id}")
    public ResponseEntity<VehicleDto> editVehicle(@PathVariable Integer id) {
//        Vehicle Vehicledto = VehicleService.getVehicleById(id);
        VehicleDto vehicledto = vehicleMapper.toDto(vehicleService.getVehicleById(id));
        if (vehicledto != null) {
            return new ResponseEntity<>(vehicledto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/paramvhl/vehicles/vehicleByAffectation/{affectation}")
    public ResponseEntity <List<VehicleDto>> getVehiclesByAffectation(@PathVariable String affectation) {
//        Vehicle Vehicledto = VehicleService.getVehicleById(id);
        List<VehicleDto> vehicledtos = vehicleMapper.toDto(vehicleService.getVehicleByAffectation(affectation));
        if (vehicledtos != null) {
            return new ResponseEntity<>(vehicledtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete Vehicle by id
    @DeleteMapping("/paramvhl/vehicles/delete/{id}")
    public ResponseEntity<HttpStatus> deleteVehicle(@PathVariable Integer id) {
        try {
            vehicleService.deleteVehicle(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
