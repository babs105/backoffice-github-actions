package sn.sastrans.backofficev2.security.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.security.models.Role;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(String rolename);

    @Query(value = "SELECT * FROM role WHERE id NOT IN (SELECT role_id FROM user_role WHERE user_id = ?1)", nativeQuery = true)
    Set<Role> getUserNotRoles(Integer userId);
}
