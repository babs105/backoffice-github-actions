package sn.sastrans.backofficev2.parameters.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.parameters.models.VehicleMake;


@Repository
public interface VehicleMakeRepository extends JpaRepository<VehicleMake,Integer> {
    VehicleMake findByTitle(String title);
}
