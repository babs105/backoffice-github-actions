package sn.sastrans.backofficev2.parameters.servicesImpl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.parameters.models.Vehicle;
import sn.sastrans.backofficev2.parameters.models.VehicleAffectation;
import sn.sastrans.backofficev2.parameters.repositories.VehicleAffectationRepository;
import sn.sastrans.backofficev2.parameters.services.VehicleAffectationService;
import sn.sastrans.backofficev2.parameters.services.VehicleService;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;

import java.util.List;

@Slf4j
@Service
public class VehicleAffectationServiceImpl implements VehicleAffectationService {

    @Autowired
    private VehicleAffectationRepository vehicleAffectionRepository;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public VehicleAffectation saveVehicleAffection(VehicleAffectation vehicleAffection) {
        return vehicleAffectionRepository.save(vehicleAffection);
    }

    @Override
    public List<VehicleAffectation> getAllVehicleAffection() {
        return vehicleAffectionRepository.findAll();
    }

    @Override
    public Page<VehicleAffectation> getAllVehicleAffection(Pageable pageable) {
        return vehicleAffectionRepository.findAll(pageable);
    }

    @Override
    public Page<VehicleAffectation> getAllVehicleAffectationByKeyword(String keyword, Pageable pageable) {
        return vehicleAffectionRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public VehicleAffectation getVehicleAffectionById(int id) {
        return vehicleAffectionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteVehicleAffection(int id) {
       log.info("in del");
//        VehicleAffectation vhlaff =  vehicleAffectionRepository.findById(id).orElse(null);
//        log.info("in del" +vhlaff.getId())     ;
//        for(Vehicle vehicle: vhlaff.getVehicles()){
//            vehicle.setVehicleaffectationid(null);
//        }
//        log.info("in del" +vhlaff);
        vehicleAffectionRepository.deleteById(id);
        }
}
