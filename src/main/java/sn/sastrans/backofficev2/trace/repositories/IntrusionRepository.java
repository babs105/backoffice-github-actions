package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.sastrans.backofficev2.trace.models.Intrusion;

public interface IntrusionRepository extends JpaRepository<Intrusion,Integer>,IntrusionRepositoryCustom {

    @Query(value = "SELECT i FROM Intrusion i WHERE concat(i.date,i.typeIntrusion,i.localisation,i.etatIntrusion) LIKE %?1% ", nativeQuery = false)
    Page<Intrusion> findByKeyword(String keyword,Pageable pageable);


//    @Query(value = "SELECT b FROM Balayage b WHERE concat(b.date,b.typeBalayage,b.localisation,b.etatBalayage) LIKE %?1% ",nativeQuery = false)
//    Page<Balayage> findByKeyword(String keyword, Pageable pageable);
}




// between ?1 and ?2  OR e.dateEvent >= ?2 AND e.dateEvent <= ?3