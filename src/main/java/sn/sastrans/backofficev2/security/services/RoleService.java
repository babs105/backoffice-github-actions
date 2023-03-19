package sn.sastrans.backofficev2.security.services;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Role saveRole(Role role);
    Page<Role> getAllRole(Pageable pageable);
    Page<Role> getAllRoleByKeyword(String keyword, Pageable pageable);
    Role getRoleById(int id);
    Role getRoleByName(String rolename);
    void deleteRole(int id);
    User assignUserRole(Integer userId, Integer roleId);
    User unassignUserRole(Integer userId, Integer roleId);

    Set<Role> getUserRoles(User user);
    Set<Role> getUserNotRoles(User user);
    List<Role> getOtherRolesUser(Integer userId);

}

