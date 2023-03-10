package sn.sastrans.backofficev2.security.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.trace.models.Evenement;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    @Query(value = "SELECT u FROM User u WHERE concat(u.firstname,u.lastname,u.username) LIKE %?1% ",nativeQuery = false)
    Page<User> findByKeyword(String keyword, Pageable pageable);

}
