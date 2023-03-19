package sn.sastrans.backofficev2.parameters.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.parameters.models.VehicleModel;
import sn.sastrans.backofficev2.parameters.repositories.VehicleModelRepository;
import sn.sastrans.backofficev2.parameters.services.VehicleModelService;

import java.util.List;

@Service
public class VehicleModelServiceImpl implements VehicleModelService {

    @Autowired
    private VehicleModelRepository vehicleModelRepository;

    @Override
    public VehicleModel saveVehicleModel(VehicleModel vehicleModel) {
        return vehicleModelRepository.save(vehicleModel);
    }

    @Override
    public List<VehicleModel> getAllVehicleModel() {
        return vehicleModelRepository.findAll();
    }

    @Override
    public VehicleModel getVehicleModelById(int id) {
        return vehicleModelRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteVehicleModel(int id) {
        vehicleModelRepository.deleteById(id);
    }

    @Override
    public VehicleModel getVehicleModelByTitle(String title) {
        return vehicleModelRepository.findByTitle(title);
    }
}
