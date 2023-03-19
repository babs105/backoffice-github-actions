package sn.sastrans.backofficev2.carburant.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.carburant.models.Rajout;

@Repository
public interface RajoutRepository extends JpaRepository<Rajout,Integer> {
}
