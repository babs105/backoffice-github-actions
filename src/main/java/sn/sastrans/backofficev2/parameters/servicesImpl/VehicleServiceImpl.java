package sn.sastrans.backofficev2.parameters.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.carburant.services.RavitaillementService;
import sn.sastrans.backofficev2.parameters.models.Vehicle;
import sn.sastrans.backofficev2.parameters.repositories.VehicleRepository;
import sn.sastrans.backofficev2.parameters.services.VehicleCategoryService;
import sn.sastrans.backofficev2.parameters.services.VehicleService;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
//    @Autowired
//    private VehicleCategoryService vehicleCategoryService;
//    @Autowired
//    private RavitaillementService ravitaillementService;

    @Autowired
    private EntityManager entityManager;
    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
//        vehicle.setKilometrageDVidange(vehicle.getKilometrageActuel()+vehicle.getCadenceVidange());
        return vehicleRepository.save(vehicle);

    }

    @Override
    public Page<Vehicle> getAllVehicle(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

    @Override
    public Page<Vehicle> getAllVehicleByKeyword(String keyword, Pageable pageable) {
        return null;
    }


    @Override
    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id).orElse(null);
    }



    @Override
    public List<Vehicle> getVehicleByAffectation(String affectation) {
        return vehicleRepository.findByVehicleaffectationid(affectation);
    }

    @Override
    public Vehicle getVehicleByMatricule(String matricule) {
        return vehicleRepository.findByMatricule(matricule);
    }


//    @Override
//    public List<Vehicle> getVehicleByAffectation(String affection) {
//        return vehicleRepository.findByAffectation(affection);
//    }

//    @Override
//    public List<Vehicle> getVehicleByAffectationId(Integer id) {
//        return vehicleRepository.findByVehicleAffectationId(id);
//    }

//    @Override
//    public List<Vehicle> getVehicleByStatus(Integer id) {
//        return vehicleRepository.findByVehiclestatusid(id);
//    }

    //    @Transactional
    @Override
    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);


    }

//    @Override
//    public List<Vehicle> getVehicleByAffectationIdAndStatus(Integer id, int status) {
//        return vehicleRepository.findByVehicleAffectationIdAndVehiclestatusid(id,status);
//    }

//    @Override
//    public List<Vehicle> getVehicleByStatus(Integer id1, Integer id2) {
//        return vehicleRepository.findVehiculeByStatus(id1,id2);
//    }


}
