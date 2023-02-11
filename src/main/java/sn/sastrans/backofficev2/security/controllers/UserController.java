package sn.sastrans.backofficev2.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import sn.sastrans.backofficev2.security.dto.JwtResponseDto;
import sn.sastrans.backofficev2.security.dto.LoginRequestDto;
import sn.sastrans.backofficev2.security.dto.MessageResponseDto;
import sn.sastrans.backofficev2.security.dto.SignupRequestDto;
import sn.sastrans.backofficev2.security.jwt.JwtUtils;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.security.models.UserPrincipal;
import sn.sastrans.backofficev2.security.services.RoleService;
import sn.sastrans.backofficev2.security.services.UserService;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Controller
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

    @GetMapping("/security/users")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAllUser());
        return "security/users";
    }

    @GetMapping("/security/user/{op}/{id}")
    public String editUser(@PathVariable Integer id, @PathVariable String op, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
        return "security/user" + op; //returns employeeEdit or employeeDetails
    }

    @PostMapping("/security/user/signin")
    @ResponseBody
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

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
    @PostMapping("/security/user/signup")
    @ResponseBody
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDto signUpRequestDto) {
        if (userService.existsByUsername(signUpRequestDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Email is already taken!"));
        }



        // Create new user's account
        User user = new User(signUpRequestDto.getFirstname(),signUpRequestDto.getLastname(),signUpRequestDto.getUsername(),encoder.encode(signUpRequestDto.getPassword()));

        Set<String> strRoles = signUpRequestDto.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleService.getRoleByName("USER");
                  if(userRole==null){
                 throw  new RuntimeException("Error: Role is not found.");
                  }

            roles.add(userRole);
        }

        user.setRoles(roles);
        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponseDto("User registered successfully!"));
    }


}
