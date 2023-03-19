package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.trace.models.Balayage;


@Repository
public interface BalayageRepository extends JpaRepository<Balayage,Integer>,BalayageRepositoryCustom {
    @Query(value = "SELECT b FROM Balayage b WHERE concat(b.date,b.typeBalayage,b.localisation,b.etatBalayage) LIKE %?1% ",nativeQuery = false)
    Page<Balayage> findByKeyword(String keyword, Pageable pageable);
}
