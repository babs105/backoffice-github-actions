package sn.sastrans.backofficev2.carburant.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.carburant.models.Ravitaillement;

import java.util.List;

@Repository
public interface RavitaillementRepository extends JpaRepository <Ravitaillement,Integer>{

//    @Query(value = "SELECT r FROM Ravitaillement r WHERE r.matriculeid LIKE %?1%",nativeQuery = false)
//    List<Ravitaillement> findByKeyword(String keyword);
    @Query(value = "SELECT r FROM Ravitaillement r WHERE " +
            "concat(r.date,r.heure,r.matriculeid,r.quantity,r.type,r.kilometrage) LIKE %?1%",nativeQuery = false)
    Page<Ravitaillement> findByKeyword(String keyword, Pageable pageable);

//    SUBDATE(CURDATE(), 1);
//    @Query(value = "SELECT MONTH(date),matriculeid, SUM(quantity)  FROM Ravitaillement where YEAR(date) = YEAR(CURDATE()) AND MONTH(date)=MONTH(CURDATE()) GROUP BY matriculeid,MONTH(date)",
//            nativeQuery = true)
//    List<Object> getConsoByVehiculeCurrentMonth();
//
//    @Query(value = "SELECT MONTH(date),matriculeid, SUM(quantity)  FROM Ravitaillement where YEAR(date) = YEAR(CURDATE()) AND MONTH(date)=MONTH(CURDATE())-1 GROUP BY matriculeid,MONTH(date)",
//            nativeQuery = true)
//    List<Object> getConsoByVehiculeLastMonth();

//    @Query(value = "SELECT date, SUM(quantity) FROM Ravitaillement GROUP BY date",
//            nativeQuery = true)
//    List<Object> getConsoByDate();

//    @Query(value = "SELECT MONTH(date), SUM(quantity) FROM Ravitaillement where YEAR(date) = YEAR(CURDATE()) GROUP BY MONTH(date) ORDER BY MONTH(date) ASC",
//            nativeQuery = true)
//    List<Object> getConsoByMonthCurrentYear();
//
//    @Query(value = "SELECT MONTH(date), SUM(quantity) FROM Ravitaillement where YEAR(date) = YEAR(CURDATE())-1 GROUP BY MONTH(date) ORDER BY MONTH(date) ASC",
//            nativeQuery = true)
//    List<Object> getConsoByMonthLastYear();
//
//    @Query(value = "SELECT MONTH(r.date),c.title,SUM(r.quantity) FROM Ravitaillement r JOIN r.vehicle v JOIN v.vehicleCategory c where YEAR(r.date) = YEAR(CURDATE()) AND MONTH(r.date) = MONTH(CURDATE())-1  GROUP BY c.title,MONTH(r.date) ",nativeQuery = false)
//    List<Object> getConsoByVHlCategoryLastMonth();
//
//    @Query(value = "SELECT MONTH(r.date),c.title,SUM(r.quantity) FROM Ravitaillement r JOIN r.vehicle v JOIN v.vehicleCategory c where YEAR(r.date) = YEAR(CURDATE()) AND MONTH(r.date) = MONTH(CURDATE()) GROUP BY c.title,MONTH(r.date) ",nativeQuery = false)
//    List<Object> getConsoByVHlCategoryCurrentMonth();
//
//
//    @Query(value = "SELECT MONTH(r.date),s.title,SUM(r.quantity) FROM Ravitaillement r JOIN r.vehicle v JOIN v.vehicleAffectation s where YEAR(r.date) = YEAR(CURDATE()) AND MONTH(r.date) = MONTH(CURDATE())-1  GROUP BY s.title,MONTH(r.date) ",nativeQuery = false)
//    List<Object> getConsoByServiceLastMonth();
//
//    @Query(value = "SELECT MONTH(r.date),s.title,SUM(r.quantity) FROM Ravitaillement r JOIN r.vehicle v JOIN v.vehicleAffectation s where YEAR(r.date) = YEAR(CURDATE()) AND MONTH(r.date) = MONTH(CURDATE()) GROUP BY s.title,MONTH(r.date) ",nativeQuery = false)
//    List<Object> getConsoByserviceCurrentMonth();

}

