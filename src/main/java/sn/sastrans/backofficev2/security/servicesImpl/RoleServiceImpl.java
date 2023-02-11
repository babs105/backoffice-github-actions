package sn.sastrans.backofficev2.security.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role getRoleByName(String rolename) {
        return roleRepository.findByName(rolename);
    }

    @Override
    public void deleteRole(int id) {

    }

    public void assignUserRole(Integer userId, Integer roleId){
        User user  = userRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }
    //Unassign Role to User
    public void unassignUserRole(Integer userId, Integer roleId){
        User user  = userRepository.findById(userId).orElse(null);
        user.getRoles().removeIf(x -> x.getId()==roleId);
        userRepository.save(user);
    }
    public Set<Role> getUserRoles(User user){
        return user.getRoles();
    }

    @Override
    public Set<Role> getUserNotRoles(User user) {
        return roleRepository.getUserNotRoles(user.getId());
    }
}
