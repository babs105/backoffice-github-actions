package sn.sastrans.backofficev2.parameters.services;


import sn.sastrans.backofficev2.parameters.models.Vehicle;

import java.util.List;

public interface VehicleTypeService {

    Vehicle saveVehicle(Vehicle vehicle);
    List<Vehicle> getAllVehicle();
    Vehicle getVehicleById(int id);
    void deleteVehicle(int id);
    
}
