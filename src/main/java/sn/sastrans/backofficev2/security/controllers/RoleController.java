package sn.sastrans.backofficev2.security.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sastrans.backofficev2.security.dto.RoleDto;
import sn.sastrans.backofficev2.security.dto.UserDto;
import sn.sastrans.backofficev2.security.mappers.UserMapper;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.security.services.RoleService;
import sn.sastrans.backofficev2.security.services.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    UserMapper userMapper;
    public Model addModelAttributes(Model model){
        model.addAttribute("roles",roleService.getAllRole());
        model.addAttribute("role", new Role());
        return model;
    }
    @GetMapping("/security/roles")
    public String getAllRole(Model model) {
        addModelAttributes(model);
        return "security/rolesInonepage";
    }

    @GetMapping("/security/role/{id}")
    public Role getById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }


//    @PostMapping("/security/rolesInonepage")
//    public String addRoleInOnePage(@Valid Role role, BindingResult result, RedirectAttributes redirAttrs){
//        if (result.hasErrors()) {
//            return "security/rolesInonepage";
//        }
//        Role roleSaved = roleService.saveRole(role);
//        if(roleSaved != null){
//            redirAttrs.addFlashAttribute("success", "role ajoute avec Succes.");
//        }else{
//            redirAttrs.addFlashAttribute("error", "Erreur creation role.");
//        }
//        return "redirect:/security/roles";
//    }

    @GetMapping("/security/role/delete/{id}")
    public String deleteRole(@PathVariable Integer id){
        roleService.deleteRole(id);
        return "redirect:/security/roles";
    }

    @GetMapping("/security/role/assign/{userId}/{roleId}")
    public ResponseEntity<UserDto> assignRole(@PathVariable Integer userId,
                                              @PathVariable Integer roleId) {


        try {
            User user =  roleService.assignUserRole(userId, roleId);
           UserDto userDto =   userMapper.toDto(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/security/role/unassign/{userId}/{roleId}")
    public ResponseEntity<UserDto> unassignRole(@PathVariable Integer userId,
                               @PathVariable Integer roleId) {
        try {
            User user = roleService.unassignUserRole(userId, roleId);
            UserDto userDto = userMapper.toDto(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
