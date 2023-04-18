package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.sastrans.backofficev2.trace.models.Desherbage;

public interface DesherbageRepository extends JpaRepository<Desherbage,Integer>,DesherbageRepositoryCustom {

    @Query(value = "SELECT d FROM Desherbage d WHERE concat(d.date,d.typeDesherbage,d.localisation,d.etatDesherbage) LIKE %?1% ", nativeQuery = false)
    Page<Desherbage> findByKeyword(String keyword,Pageable pageable);


//    @Query(value = "SELECT b FROM Balayage b WHERE concat(b.date,b.typeBalayage,b.localisation,b.etatBalayage) LIKE %?1% ",nativeQuery = false)
//    Page<Balayage> findByKeyword(String keyword, Pageable pageable);
}




// between ?1 and ?2  OR e.dateEvent >= ?2 AND e.dateEvent <= ?3