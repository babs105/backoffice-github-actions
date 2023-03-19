package sn.sastrans.backofficev2.parameters.services;


import sn.sastrans.backofficev2.parameters.models.VehicleCategory;

import java.util.List;

public interface VehicleCategoryService {

    VehicleCategory saveVehicleCategory(VehicleCategory vehicleCategory);
    List<VehicleCategory> getAllVehicleCategory();
    VehicleCategory getVehicleCategoryById(int id);

    VehicleCategory getVehicleCategoryByTitle(String title);
    void deleteVehicleCategory(int id);
    
}
