package sn.sastrans.backofficev2.security.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sn.sastrans.backofficev2.exception.BadRequestException;
import sn.sastrans.backofficev2.security.dto.*;
import sn.sastrans.backofficev2.security.jwt.JwtUtils;
import sn.sastrans.backofficev2.security.mappers.RoleMapper;
import sn.sastrans.backofficev2.security.mappers.UserMapper;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.security.models.UserPrincipal;
import sn.sastrans.backofficev2.security.services.RoleService;
import sn.sastrans.backofficev2.security.services.UserService;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;

    //    @GetMapping("/security/users")
//    public String getAll(Model model) {
//        model.addAttribute("users", userService.getAllUser());
//        return "security/users";
//    }
    @GetMapping("/security/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

            List<User> users = new ArrayList<User>();
            Pageable paging = PageRequest.of(page, size);

            Page<User> pageUsers;
            if (title == null) pageUsers = userService.getAllUser(paging);
            else pageUsers = userService.getAllUserByKeyword(title, paging);

            users = pageUsers.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("users", userMapper.toDto(users));
            response.put("currentPage", pageUsers.getNumber());
            response.put("totalItems", pageUsers.getTotalElements());
            response.put("totalPages", pageUsers.getTotalPages());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }


    @GetMapping("/security/user/{id}/otherroles")
    public ResponseEntity<?> getOtherRolesUsers(@PathVariable Integer id) {

            List<Role> othersRoles = roleService.getOtherRolesUser(id);
            List<RoleDto> rolesDtos = roleMapper.toDto(othersRoles);

            return new ResponseEntity<List<RoleDto>>(rolesDtos, HttpStatus.OK);


    }

    @PostMapping("/public/user/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseDto(jwt,
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getUsername(),
                roles
        ));
    }

    @PostMapping("/public/user/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto signUpRequestDto) {



        if (userService.existsByUsername(signUpRequestDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Email is already taken!"));
        }


        // Create new user's account
        User user = new User(signUpRequestDto.getFirstname(), signUpRequestDto.getLastname(), signUpRequestDto.getUsername(), encoder.encode(signUpRequestDto.getPassword()));

        Set<String> strRoles = signUpRequestDto.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
          Role userRole = roleService.getRoleByName("USER");
//            Role userRole = new Role(2,"USER","access to home page");
            if (userRole == null) {
                throw new RuntimeException("Error: Role is not found.");
            }

            roles.add(userRole);
        }

        user.setRoles(roles);
        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponseDto("Compte créé avec succes!"));
    }

    @GetMapping("/private/user/{id}")
    public ResponseEntity<?> editUser(@PathVariable Integer id) {
        UserDto userDto = userMapper.toDto(userService.getUserById(id));
            return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }
    @GetMapping("/private/user/username/{username}")
    public ResponseEntity<?> editUserByUsern(@PathVariable String username) {
//
        UserDto userDto = userMapper.toDto(userService.getUserByUsername(username));
            return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @DeleteMapping("/security/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {

        userService.deleteUser(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/signout")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal UserDto user) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
//

}
