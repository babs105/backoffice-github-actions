package sn.sastrans.backofficev2.security.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sastrans.backofficev2.security.dto.RoleDto;
import sn.sastrans.backofficev2.security.dto.UserDto;
import sn.sastrans.backofficev2.security.mappers.RoleMapper;
import sn.sastrans.backofficev2.security.mappers.UserMapper;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.security.services.RoleService;
import sn.sastrans.backofficev2.security.services.UserService;
import sn.sastrans.backofficev2.trace.dto.RemorquageDto;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    RoleMapper roleMapper;

    @GetMapping("/security/roles")
    public ResponseEntity<Map<String, Object>> getAllRoles(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        try {
            List<Role> roles = new ArrayList<Role>();
            Pageable paging = PageRequest.of(page, size, Sort.by("id").descending());

            Page<Role> pageRoles;
            if (title == null) pageRoles = roleService.getAllRole(paging);
            else pageRoles = roleService.getAllRoleByKeyword(title, paging);

            roles = pageRoles.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("roles", roleMapper.toDto(roles));
            response.put("currentPage", pageRoles.getNumber());
            response.put("totalItems", pageRoles.getTotalElements());
            response.put("totalPages", pageRoles.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/security/role/{id}")
    public Role getById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }


    @PostMapping("/security/roles")
    public ResponseEntity<RoleDto> addRoleInOnePage(@RequestBody @Valid RoleDto roleDto) {

        try {
            Role roleSaved = roleService.saveRole(roleMapper.toEntity(roleDto));
            RoleDto role = roleMapper.toDto(roleSaved);
            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/security/role/update/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Integer id, @RequestBody @Valid RoleDto roleDto) {

        try {
            Role roleEntity = roleMapper.toEntity(roleDto);
            roleEntity.setId(id);
            Role RoleSaved = roleService.saveRole(roleEntity);

            if (RoleSaved != null) {
          return new ResponseEntity<>(roleDto, HttpStatus.OK);
//                return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleDto);
            }
            else  return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.info("error mess", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/security/role/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Integer id) {
        try {
            roleService.deleteRole(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/security/role/assign/{userId}/{roleId}")
    public ResponseEntity<UserDto> assignRole(@PathVariable Integer userId,
                                              @PathVariable Integer roleId) {


        try {
            User user = roleService.assignUserRole(userId, roleId);
            UserDto userDto = userMapper.toDto(user);
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
