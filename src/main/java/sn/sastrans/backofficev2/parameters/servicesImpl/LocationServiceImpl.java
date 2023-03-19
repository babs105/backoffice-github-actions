package sn.sastrans.backofficev2.parameters.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.parameters.models.Location;
import sn.sastrans.backofficev2.parameters.repositories.LocationRepository;
import sn.sastrans.backofficev2.parameters.services.LocationService;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;
    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationById(int id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteLocation(int id) {
     locationRepository.deleteById(id);
    }
}
