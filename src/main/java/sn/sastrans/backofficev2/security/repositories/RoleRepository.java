package sn.sastrans.backofficev2.security.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> ,RoleRepositoryCustom{
    Role findByName(String rolename);

    @Query(value = "SELECT * FROM role WHERE id NOT IN (SELECT role_id FROM user_role WHERE user_id = ?1)", nativeQuery = true)
    Set<Role> getUserNotRoles(Integer userId);

    @Query(value = "SELECT r FROM Role r WHERE concat(r.name,r.description) LIKE %?1% ",nativeQuery = false)
    Page<Role> findByKeyword(String keyword, Pageable pageable);
}
