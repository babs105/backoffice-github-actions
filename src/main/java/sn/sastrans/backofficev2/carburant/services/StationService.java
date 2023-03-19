package sn.sastrans.backofficev2.carburant.services;


import sn.sastrans.backofficev2.carburant.models.Station;

import java.util.List;

public interface StationService {
    Station saveStation(Station station);
    List<Station> getAllStation();
    Station getStationById(int id);
    Station getStationByName(String stationname);
    void deleteStation(int id);
}
