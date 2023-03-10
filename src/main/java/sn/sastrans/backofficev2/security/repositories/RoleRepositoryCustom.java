package sn.sastrans.backofficev2.security.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.sastrans.backofficev2.security.models.Role;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepositoryCustom {
    List<Role> getOtherRolesUser(Integer userId);
}
