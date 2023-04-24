package sn.sastrans.backofficev2.traceTest.securityTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sn.sastrans.backofficev2.security.controllers.UserController;
import sn.sastrans.backofficev2.security.dto.*;
import sn.sastrans.backofficev2.security.jwt.JwtUtils;
import sn.sastrans.backofficev2.security.mappers.RoleMapper;
import sn.sastrans.backofficev2.security.mappers.UserMapper;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.security.models.UserPrincipal;
import sn.sastrans.backofficev2.security.services.RoleService;
import sn.sastrans.backofficev2.security.services.UserService;
import sn.sastrans.backofficev2.trace.controllers.EvenementController;
import sn.sastrans.backofficev2.trace.dto.EvenementDto;
import sn.sastrans.backofficev2.trace.mappers.EvenementMapper;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.services.EvenementService;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserControllerTest {
    //    private static  final String END_POINT_PATH="/user/evenements";
    private static final String jwtSecret = "bezKoderSecretKey";
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

//    @Value("${jwt.secret}")
//    private String jwtSecret;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;
    @MockBean
    private UserMapper userMapper;

    @MockBean
    private RoleMapper roleMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    public void testGetAllUsers() throws Exception {
        //given
        String title = "";
        Integer pageNumber = 0;
        Integer size = 5;
        Pageable paging = PageRequest.of(pageNumber, size);

        User user = User.builder()
                .firstname("babacar")
                .lastname("Dieng")
                .username("baba@gmail.com")
                .password("passer2002")
                .roles(null)
                .build();

        List<User> userList = Arrays.asList(user);

        int totaluser = userList.size();

        if (title.length() > 0) {
            userList = userList.stream().filter(item -> item.getUsername().contains(title)).collect(Collectors.toList());
        }
        Page<User> userPage = new PageImpl<>(userList, paging, totaluser);

        if (title.length() > 0) {
            when(userService.getAllUserByKeyword(any(), any())).thenReturn(userPage);
        } else {
            when(userService.getAllUser(any())).thenReturn(userPage);
        }


        UserDto userDto = UserDto.builder()
                .id(1)
                .firstname("babacar")
                .lastname("Dieng")
                .username("baba@gmail.com")
                .roles(null)
                .build();

        List<UserDto> userDtoList = Arrays.asList(userDto);

        int totaluserDto = userDtoList.size();
        if (title.length() > 0) {
            userDtoList = userDtoList.stream().filter(item -> item.getUsername().contains(title)).collect(Collectors.toList());
        }
        Page<UserDto> userDtoPage = new PageImpl<>(userDtoList, paging, totaluserDto);
        when(userMapper.toDto(anyList())).thenReturn(userDtoPage.getContent());

        //when
        mockMvc.perform(get("/security/users")
                        .param("title", title)
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.users.size()").value(1))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andDo(print());


    }


    @Test
    public void testGetOtherRolesUsers() throws Exception {
        //Given
        List<Role> allRoles = new ArrayList<>();
        List<Role> myRoles = new ArrayList<>();
        List<Role> otherRoles = new ArrayList<>();
        Role roleUser = Role.builder()
                .id(1).name("USER").description("Access Home Page")
                .build();
        Role roleSuperAdmin = Role.builder()
                .id(1).name("SUPER_ADMIN").description("Access All Module")
                .build();

        Role roleUserTrace = Role.builder()
                .id(1).name("USER_TRACE").description("Access Trace Module")
                .build();
        allRoles.add(roleUser);
        allRoles.add(roleUserTrace);
        allRoles.add(roleSuperAdmin);

        myRoles.add(roleUser);
        myRoles.add(roleUserTrace);

        User user = User.builder()
                .id(1)
                .firstname("babacar")
                .lastname("Dieng")
                .username("baba@gmail.com")
                .password("passer2002")
                .roles(new HashSet<>(myRoles))
                .build();


        for (int i = 0; i < allRoles.size(); i++) {
            boolean present = false;
            for (int j = 0; j < myRoles.size(); j++) {
                if (allRoles.get(i).getName().equals(myRoles.get(j).getName())) {
                    present = true;
                    break;
                }
            }
            if (present == false) {
                otherRoles.add(allRoles.get(i));
            }
        }

        List<RoleDto> otherRoleDtos = otherRoles.stream().map(role ->
                        RoleDto.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .description(role.getDescription())
                                .build())
                .collect(Collectors.toList());

        when(roleService.getOtherRolesUser(any())).thenReturn(otherRoles);
        when(roleMapper.toDto(otherRoles)).thenReturn(otherRoleDtos);

        //when
        mockMvc.perform(get("/security/user/" + user.getId() + "/otherroles")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name", is("SUPER_ADMIN")))
                .andDo(print());


    }

    @Test
    public void testAuthenticateUser() throws Exception {
// prepare test data

        Role role = Role.builder()
                .id(1)
                .name("USER")
                .description("Access Home Page")
                .build();

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("babacar.dieng41@gmail.com");
        loginRequestDto.setPassword("passer2020");

        User user = User.builder()
                  .id(1)
                  .firstname("babacar")
                  .lastname("dieng")
                  .username(loginRequestDto.getUsername())
                  .password(loginRequestDto.getPassword())
                  .roles(new HashSet<>(Collections.singleton(role)))
                  .build();

//        UserDetails userPrincipal = new UserPrincipal(user) ;
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword());

        // mock service method
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("mytoken");
        when(userService.getUserByUsername(any())).thenReturn(user);

        // perform the request
   mockMvc.perform(MockMvcRequestBuilders.post("/public/user/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequestDto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.token").value("mytoken"))
               .andExpect(jsonPath("$.firstName").value("babacar"))
               .andExpect(jsonPath("$.lastName").value("dieng"))
               .andExpect(jsonPath("$.username").value(user.getUsername()))
              .andExpect(jsonPath("$.roles").value(user.getRoles().stream().map(irole -> irole.getName()).collect(Collectors.toList())))
               .andDo(print());


////        // verify the response
//        String responseJson = mvcResult.getResponse().getContentAsString();
//        JwtResponseDto response = objectMapper.readValue(responseJson, JwtResponseDto.class);
//        assertNotNull(response.getToken());
////
////        // verify the token
//        String usernameFromToken  = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//        assertEquals(user.getUsername(), usernameFromToken);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils,times(1)).generateJwtToken(authentication);
        verify(userService,times(1)).getUserByUsername(any());
    }




    @Test
    public void testRegisterUser() throws Exception {
     // prepare test data

       Role role = Role.builder()
                .id(1)
                .name("USER")
                .description("Access Home Page")
                .build();

        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .firstname("Babacar")
                .lastname("Dieng")
                .username("babacar.dieng4111@gmail.com")
                .password("passer2020")
                .role(null)
        .build();

        User user = User.builder()
                .lastname(signupRequestDto.getLastname())
                .firstname(signupRequestDto.getFirstname())
                .password(signupRequestDto.getPassword())
                .roles(new HashSet<>(Arrays.asList(role)))
                .build();


        // mock service method
        when(roleService.getRoleByName(any())).thenReturn(role);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        String requestBody = objectMapper.writeValueAsString(signupRequestDto);


        // perform the request
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/public/user/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andReturn();
        // verify the response
        String responseJson = mvcResult.getResponse().getContentAsString();
        MessageResponseDto messageResponseDto = objectMapper.readValue(responseJson, MessageResponseDto.class);

        assertAll(
                () -> {
                    assertEquals("Compte crÃ©Ã© avec succes!", messageResponseDto.getMessage());
                }
        );
    }

    @Test
    public void testEditUser() throws Exception {
        // prepare test data
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setFirstname("Babs");
        updatedUser.setLastname("DiengSalla");
        updatedUser.setUsername("updateduser@test.com");
        updatedUser.setPassword("updatedpassword");
        updatedUser.setRoles(new HashSet<>(Arrays.asList(Role.builder().id(1).name("USER").description("Access Home Page").build())));


        // mock service method
        when(userService.updateUser(eq(1), any(User.class))).thenReturn(updatedUser);

        // perform the request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/private/user/update/"+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                        .andExpect(status().isCreated())
                        .andReturn();

        // verify the response
        String responseJson = result.getResponse().getContentAsString();
        User user2 = objectMapper.readValue(responseJson, User.class);
        assertEquals(updatedUser.getUsername(), user2.getUsername());
        assertEquals(updatedUser.getFirstname(), user2.getFirstname());
    }

    @Test
    public void testEditUserByUsername() throws Exception {

    }

   @Test
    public void testDeleteUser() throws Exception {
       // mock service method
       doNothing().when(userService).deleteUser(eq(1));

       // perform the request
    mockMvc.perform(delete("/security/user/delete/"+1))
//                       .header("Authorization", "Bearer " + createToken("testuser")))
               .andExpect(status().isNoContent())
            .andDo(print());

       // verify the response
       verify(userService, times(1)).deleteUser(eq(1));
    }
    @Test
    public  void testSignOut() throws Exception {


    }
}
