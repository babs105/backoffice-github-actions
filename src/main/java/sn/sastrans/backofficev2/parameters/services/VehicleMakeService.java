package sn.sastrans.backofficev2.parameters.services;


import sn.sastrans.backofficev2.parameters.models.VehicleMake;

import java.util.List;

public interface VehicleMakeService {

    VehicleMake saveVehicleMake(VehicleMake vehicleMake);
    List<VehicleMake> getAllVehicleMake();
    VehicleMake getVehicleMakeById(int id);
    void deleteVehicleMake(int id);

    VehicleMake getVehicleMakeByTitle(String title);
}
