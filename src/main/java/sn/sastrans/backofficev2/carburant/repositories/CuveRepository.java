package sn.sastrans.backofficev2.carburant.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.security.models.Role;

@Repository
public interface CuveRepository extends JpaRepository <Cuve,Integer>{
    Cuve findByCuveName(String cuvename);

    @Query(value = "SELECT c FROM Cuve c WHERE concat(c.cuveName,c.quantity) LIKE %?1% ",nativeQuery = false)
    Page<Cuve> findByKeyword(String keyword, Pageable pageable);
}
