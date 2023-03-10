package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.sastrans.backofficev2.trace.models.DetailAccident;

import java.util.Date;
import java.util.List;

public interface DetailAccidentRepository extends JpaRepository<DetailAccident,Integer>,DetailAccidentRepositoryCustom {
    @Query(value = "SELECT d FROM DetailAccident d WHERE concat(d.typeAccident,d.matriculeVhlImplique) LIKE %?1%",nativeQuery = false)
    Page<DetailAccident> findByKeyword(String keyword, Pageable pageable);

    List<DetailAccident> findByEventid(Integer id);



    @Query(value ="SELECT a FROM DetailAccident a JOIN a.evenement e WHERE((CONCAT(e.localisation,a.typeAccident,a.matriculeVhlImplique) LIKE %?1% ) AND (e.dateEvent BETWEEN ?2 AND ?3))",nativeQuery = false)
    Page<DetailAccident> findAll(String keyword, Date dateDebut, Date dateFin, Pageable pageable);
//    @Query(value = "SELECT d FROM Evenement e  JOIN e.detailAccident d WHERE((CONCAT(e.localisation,d.typeAccident) LIKE %?1% ) AND (e.dateEvent BETWEEN ?2 AND ?3))",nativeQuery = false)
//    Page<DetailAccident> findAll(String keyword, Date dateDebut, Date dateFin, Pageable pageable);
}
