package sn.sastrans.backofficev2.carburant.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.carburant.models.Station;

@Repository
public interface StationRepository extends JpaRepository <Station,Integer>{
    Station findByStationName(String stationname);
}
