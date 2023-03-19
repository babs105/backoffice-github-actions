package sn.sastrans.backofficev2.parameters.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.parameters.models.VehicleMake;
import sn.sastrans.backofficev2.parameters.repositories.VehicleMakeRepository;
import sn.sastrans.backofficev2.parameters.services.VehicleMakeService;

import java.util.List;

@Service
public class VehicleMakeServiceImpl implements VehicleMakeService {

    @Autowired
    private VehicleMakeRepository vehicleMakeRepository;

    @Override
    public VehicleMake saveVehicleMake(VehicleMake vehicleMake) {
        return vehicleMakeRepository.save(vehicleMake);
    }

    @Override
    public List<VehicleMake> getAllVehicleMake() {
        return vehicleMakeRepository.findAll();
    }

    @Override
    public VehicleMake getVehicleMakeById(int id) {
        return vehicleMakeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteVehicleMake(int id) {
        vehicleMakeRepository.deleteById(id);
    }

    @Override
    public VehicleMake getVehicleMakeByTitle(String title) {
        return vehicleMakeRepository.findByTitle(title);
    }
}
