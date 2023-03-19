package sn.sastrans.backofficev2.parameters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.parameters.models.VehicleCategory;


@Repository
public interface VehicleCategoryRepository extends JpaRepository<VehicleCategory,Integer> {
    VehicleCategory findByTitle(String title);
}
