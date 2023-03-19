package sn.sastrans.backofficev2.carburant.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.carburant.models.Station;
import sn.sastrans.backofficev2.carburant.repositories.StationRepository;
import sn.sastrans.backofficev2.carburant.services.StationService;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;
    @Override
    public Station saveStation(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public List<Station> getAllStation() {
        return stationRepository.findAll();
    }

    @Override
    public Station getStationById(int id) {
        return stationRepository.findById(id).orElse(null);

    }

    @Override
    public Station getStationByName(String stationname) {
        return stationRepository.findByStationName(stationname);
    }

    @Override
    public void deleteStation(int id) {
       stationRepository.deleteById(id);
    }
}
