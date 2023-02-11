package sn.sastrans.backofficev2.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.services.RoleService;
import sn.sastrans.backofficev2.security.services.UserService;

import javax.validation.Valid;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;
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
    @ResponseBody
    public Role getById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }


    @PostMapping("/security/rolesInonepage")
    public String addRoleInOnePage(@Valid Role role, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "security/rolesInonepage";
        }
        Role roleSaved = roleService.saveRole(role);
        if(roleSaved != null){
            redirAttrs.addFlashAttribute("success", "role ajoute avec Succes.");
        }else{
            redirAttrs.addFlashAttribute("error", "Erreur creation role.");
        }
        return "redirect:/security/roles";
    }

    @GetMapping("/security/role/delete/{id}")
    public String deleteRole(@PathVariable Integer id){
        roleService.deleteRole(id);
        return "redirect:/security/roles";
    }

    @RequestMapping("/security/role/assign/{userId}/{roleId}")
    public String assignRole(@PathVariable Integer userId,
                             @PathVariable Integer roleId) {
        roleService.assignUserRole(userId, roleId);
        return "redirect:/security/user/Edit/" + userId;
    }

    @RequestMapping("/security/role/unassign/{userId}/{roleId}")
    public String unassignRole(@PathVariable Integer userId,
                               @PathVariable Integer roleId) {
        roleService.unassignUserRole(userId, roleId);
        return "redirect:/security/user/Edit/" + userId;
    }
}
