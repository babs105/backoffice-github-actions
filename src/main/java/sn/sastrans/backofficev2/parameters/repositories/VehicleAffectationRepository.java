package sn.sastrans.backofficev2.parameters.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.parameters.models.VehicleAffectation;

import java.util.List;


@Repository
public interface VehicleAffectationRepository extends JpaRepository<VehicleAffectation,Integer> {



    @Query(value = "SELECT v FROM VehicleAffectation v WHERE concat(v.title) LIKE %?1% ",nativeQuery = false)
    Page<VehicleAffectation> findByKeyword(String keyword, Pageable pageable);
}
