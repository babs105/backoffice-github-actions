package sn.sastrans.backofficev2.parameters.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.parameters.models.VehicleModel;


@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModel,Integer> {
    VehicleModel findByTitle(String title);
}
