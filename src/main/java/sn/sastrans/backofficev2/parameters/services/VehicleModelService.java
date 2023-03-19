package sn.sastrans.backofficev2.parameters.services;



import sn.sastrans.backofficev2.parameters.models.VehicleModel;

import java.util.List;

public interface VehicleModelService {

    VehicleModel saveVehicleModel(VehicleModel vehicleModel);
    List<VehicleModel> getAllVehicleModel();
    VehicleModel getVehicleModelById(int id);
    void deleteVehicleModel(int id);

    VehicleModel getVehicleModelByTitle(String title);
    
}
