package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import java.util.Date;
import java.util.List;

public interface RemorquageRepository extends JpaRepository<Remorquage,Integer>,RemorquageRepositoryCustom {
    @Query(value = "SELECT r FROM Remorquage r WHERE concat(r.nomROM,r.lieuDepot) LIKE %?1%",nativeQuery = false)
    Page<Remorquage> findByKeyword(String keyword, Pageable pageable);

    @Query(value = "SELECT r FROM Evenement e RIGHT JOIN e.remorquages r " +
            "WHERE((CONCAT(e.localisation,e.matVehicule,r.nomROM,r.matriculeDep,r.lieuDepot) " +
            "LIKE %?1% )" +
            "AND (e.dateEvent BETWEEN ?2 AND ?3))",nativeQuery = false)
    Page<Remorquage> findAll(String keyword, Date dateDebut, Date dateFin, Pageable pageable);



    @Query(value ="SELECT r.catVhlRemorque,COUNT(*) FROM Remorquage r JOIN r.evenement e  where e.secteur LIKE 'AMT' AND YEAR(r.dateRom) = YEAR(CURDATE()) AND MONTH(r.dateRom) = MONTH(CURDATE())-1  GROUP BY r.catVhlRemorque",nativeQuery = false)
    List<Object> getNombreRomByCatAmtLastMonth();

    @Query(value ="SELECT r.catVhlRemorque,COUNT(*) FROM Remorquage r JOIN r.evenement e  where e.secteur LIKE 'AMT' AND YEAR(r.dateRom) = YEAR(CURDATE()) AND MONTH(r.dateRom) = MONTH(CURDATE())  GROUP BY r.catVhlRemorque",nativeQuery = false)
    List<Object> getNombreRomByCatAmtCurrentMonth();


    @Query(value ="SELECT r.catVhlRemorque,COUNT(*) FROM Remorquage r JOIN r.evenement e  where e.secteur LIKE 'TT' AND YEAR(r.dateRom) = YEAR(CURDATE()) AND MONTH(r.dateRom) = MONTH(CURDATE())-1  GROUP BY r.catVhlRemorque",nativeQuery = false)
    List<Object> getNombreRomByCatTTLastMonth();

    @Query(value ="SELECT r.catVhlRemorque,COUNT(*) FROM Remorquage r JOIN r.evenement e  where e.secteur LIKE 'TT' AND YEAR(r.dateRom) = YEAR(CURDATE()) AND MONTH(r.dateRom) = MONTH(CURDATE())  GROUP BY r.catVhlRemorque",nativeQuery = false)
    List<Object> getNombreRomByCatTTCurrentMonth();
}


// between ?1 and ?2  OR e.dateEvent >= ?2 AND e.dateEvent <= ?3