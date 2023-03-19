package sn.sastrans.backofficev2.parameters.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.parameters.models.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle saveVehicle(Vehicle vehicle);
//    List<Vehicle> getAllVehicle();
    Page<Vehicle> getAllVehicle(Pageable pageable);
    Page<Vehicle> getAllVehicleByKeyword(String keyword, Pageable pageable);

   List<Vehicle> getVehicleByAffectation(String affectation);
    Vehicle getVehicleById(int id);

    void deleteVehicle(int id);
    Vehicle getVehicleByMatricule(String matricule);

//    List<Vehicle> getVehicleByAffectationId(Integer id);

//    List<Vehicle> getVehicleByStatus(Integer id);


//    List<Vehicle> getVehicleByAffectationIdAndStatus(Integer id, int status);


//    List<Vehicle> getVehicleByStatus(Integer id1, Integer id2);
}
