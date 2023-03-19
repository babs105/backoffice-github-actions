package sn.sastrans.backofficev2.parameters.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.parameters.models.VehicleCategory;
import sn.sastrans.backofficev2.parameters.repositories.VehicleCategoryRepository;
import sn.sastrans.backofficev2.parameters.services.VehicleCategoryService;

import java.util.List;

@Service
public class VehicleCategoryServiceImpl implements VehicleCategoryService {

    @Autowired
    private VehicleCategoryRepository vehicleCategoryRepository;

    @Override
    public VehicleCategory saveVehicleCategory(VehicleCategory vehicleCategory) {
        return vehicleCategoryRepository.save(vehicleCategory);
    }

    @Override
    public List<VehicleCategory> getAllVehicleCategory() {
        return vehicleCategoryRepository.findAll();
    }

    @Override
    public VehicleCategory getVehicleCategoryById(int id) {
        return vehicleCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public VehicleCategory getVehicleCategoryByTitle(String title) {
        return vehicleCategoryRepository.findByTitle(title);
    }

    @Override
    public void deleteVehicleCategory(int id) {
        vehicleCategoryRepository.deleteById(id);
    }
}
