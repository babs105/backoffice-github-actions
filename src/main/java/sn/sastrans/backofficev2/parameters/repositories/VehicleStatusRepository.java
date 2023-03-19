package sn.sastrans.backofficev2.parameters.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.parameters.models.VehicleStatus;


@Repository
public interface VehicleStatusRepository extends JpaRepository<VehicleStatus,Integer> {
}
