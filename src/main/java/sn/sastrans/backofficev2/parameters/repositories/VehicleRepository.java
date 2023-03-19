package sn.sastrans.backofficev2.parameters.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.parameters.models.Vehicle;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {
   List<Vehicle> findByVehicleaffectationid(String affectation);
   Vehicle findByMatricule(String matricule);
//   List<Vehicle> findByVehiclestatusid(int id);
//   List<Vehicle> findByVehicleAffectationIdAndVehiclestatusid(Integer id,int status);



//   @Query(value = "SELECT v FROM Vehicle v WHERE v.vehiclestatusid =?1 OR v.vehiclestatusid =?2",nativeQuery = false)
//   List<Vehicle> findVehiculeByStatus(Integer id1,Integer id2);

   @Query(value = "SELECT v FROM Vehicle v WHERE concat(v.matricule) LIKE %?1% ORDER BY v.acquisitionDate DESC ",nativeQuery = false)
   Page<Evenement> findByKeyword(String keyword, Pageable pageable);

}
