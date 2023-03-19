package sn.sastrans.backofficev2.parameters.services;


import sn.sastrans.backofficev2.parameters.models.VehicleStatus;

import java.util.List;

public interface VehicleStatusService {

    VehicleStatus saveVehicleStatus(VehicleStatus vehicleStatus);
    List<VehicleStatus> getAllVehicleStatus();
    VehicleStatus getVehicleStatusById(int id);
    void deleteVehicleStatus(int id);
    
}
