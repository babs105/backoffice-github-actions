package sn.sastrans.backofficev2.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.security.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);

}
