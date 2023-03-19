package sn.sastrans.backofficev2.parameters.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.parameters.models.VehicleAffectation;

import java.util.List;

public interface VehicleAffectationService {

    VehicleAffectation saveVehicleAffection(VehicleAffectation vehicleAffection);
    List<VehicleAffectation> getAllVehicleAffection();
    Page<VehicleAffectation> getAllVehicleAffection(Pageable pageable);
    Page<VehicleAffectation> getAllVehicleAffectationByKeyword(String keyword, Pageable pageable);
    VehicleAffectation getVehicleAffectionById(int id);
    void deleteVehicleAffection(int id);
    
}
