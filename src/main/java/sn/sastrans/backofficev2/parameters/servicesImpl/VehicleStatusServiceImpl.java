package sn.sastrans.backofficev2.parameters.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.parameters.models.VehicleStatus;
import sn.sastrans.backofficev2.parameters.repositories.VehicleStatusRepository;
import sn.sastrans.backofficev2.parameters.services.VehicleStatusService;

import java.util.List;

@Service
public class VehicleStatusServiceImpl implements VehicleStatusService {

    @Autowired
    private VehicleStatusRepository vehicleStatusRepository;

    @Override
    public VehicleStatus saveVehicleStatus(VehicleStatus vehicleStatus) {
        return vehicleStatusRepository.save(vehicleStatus);
    }

    @Override
    public List<VehicleStatus> getAllVehicleStatus() {
        return vehicleStatusRepository.findAll();
    }

    @Override
    public VehicleStatus getVehicleStatusById(int id) {
        return vehicleStatusRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteVehicleStatus(int id) {
        vehicleStatusRepository.deleteById(id);
    }
}
