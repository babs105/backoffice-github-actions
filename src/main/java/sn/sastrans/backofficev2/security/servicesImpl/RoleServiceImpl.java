package sn.sastrans.backofficev2.security.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.exception.RoleNotFoundException;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.security.repositories.RoleRepository;
import sn.sastrans.backofficev2.security.repositories.UserRepository;
import sn.sastrans.backofficev2.security.services.RoleService;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> getAllRole(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Page<Role> getAllRoleByKeyword(String keyword, Pageable pageable) {
        return roleRepository.findByKeyword(keyword,pageable);
    }


    @Override
    public Role getRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role getRoleByName(String rolename) {
        Role role = roleRepository.findByName(rolename);
        if(role==null){
            throw new RoleNotFoundException("Role n'existe pase");
        }
        return role;

    }

    @Override
    public void deleteRole(int id) {
      Role role =  roleRepository.findById(id).orElse(null);
     for(User user: role.getUsers()){
           user.getRoles().remove(role);
        }
        roleRepository.deleteById(id);
    }

    public User assignUserRole(Integer userId, Integer roleId){
        User user  = userRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
       return userRepository.save(user);
    }
    //Unassign Role to User
    public User unassignUserRole(Integer userId, Integer roleId){
        User user  = userRepository.findById(userId).orElse(null);
        user.getRoles().removeIf(x -> x.getId()==roleId);
     return    userRepository.save(user);
    }
    public Set<Role> getUserRoles(User user){
        return user.getRoles();
    }

    @Override
    public Set<Role> getUserNotRoles(User user) {
        return roleRepository.getUserNotRoles(user.getId());
    }

    @Override
    public List<Role> getOtherRolesUser(Integer userId) {
        return roleRepository.getOtherRolesUser(userId);
    }


}
