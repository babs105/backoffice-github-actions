package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.trace.models.Evenement;


@Repository
public interface EvenementRepository extends JpaRepository<Evenement,Integer>,EvenementRepositoryCustom {
    @Query(value = "SELECT e FROM Evenement e WHERE concat(e.dateEvent,e.natureEvent,e.matVehicule,e.etatEvent,e.localisation,e.statutEvent) LIKE %?1% ",nativeQuery = false)
    Page<Evenement> findByKeyword(String keyword, Pageable pageable);
}
