package sn.sastrans.backofficev2.parameters.services;


import sn.sastrans.backofficev2.parameters.models.Location;

import java.util.List;

public interface LocationService {

    Location saveLocation(Location location);
    List<Location> getAllLocation();
    Location getLocationById(int id);
    void deleteLocation(int id);
    
}
