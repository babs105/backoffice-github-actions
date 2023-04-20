package sn.sastrans.backofficev2.traceTest.securityTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sn.sastrans.backofficev2.security.controllers.UserController;
import sn.sastrans.backofficev2.security.dto.RoleDto;
import sn.sastrans.backofficev2.security.dto.UserDto;
import sn.sastrans.backofficev2.security.mappers.UserMapper;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.security.services.UserService;
import sn.sastrans.backofficev2.trace.controllers.EvenementController;
import sn.sastrans.backofficev2.trace.dto.EvenementDto;
import sn.sastrans.backofficev2.trace.mappers.EvenementMapper;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.services.EvenementService;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {
//    private static  final String END_POINT_PATH="/user/evenements";

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    public void testGetAllUsers() throws Exception {
        //given
        String title="";
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

        int totaluser=  userList.size();

        if(title.length()>0) {
            userList = userList.stream().filter(item -> item.getUsername().contains(title)).collect(Collectors.toList());
        }
        Page<User> userPage = new PageImpl<>(userList,paging,totaluser);

        if(title.length()>0){
            when(userService.getAllUserByKeyword(any(),any())).thenReturn(userPage);
        }else{
            when(userService.getAllUser(any())).thenReturn(userPage) ;
        }


        UserDto userDto = UserDto.builder()
                .id(1)
                .firstname("babacar")
                .lastname("Dieng")
                .username("baba@gmail.com")
                .roles(null)
                .build();

        List<UserDto> userDtoList = Arrays.asList(userDto);

        int totaluserDto =  userDtoList.size();
        if(title.length()>0) {
            userDtoList = userDtoList.stream().filter(item -> item.getUsername().contains(title)).collect(Collectors.toList());
        }
        Page<UserDto> userDtoPage = new PageImpl<>(userDtoList,paging,totaluserDto);
        when(userMapper.toDto(anyList())).thenReturn(userDtoPage.getContent());

        //when
        mockMvc.perform(get("/security/users")
                        .param("title",title)
                        .param("page","0")
                        .param("size","5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.users.size()").value(1))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andDo(print());


    }


    @Test
    public void testGetOtherRolesUsers() throws Exception {

    }

    @Test
    public void testAuthenticateUser() throws Exception {

    }

    @Test
    public void testRegisterUser() throws Exception {

    }

    @Test
    public void testEditUser() throws Exception {
    }

    @Test
    public void testEditUserByUsername() throws Exception {

    }

   @Test
    public void testDeleteUser() throws Exception {

    }
    @Test
    public  void testSignOut() throws Exception {


    }
}
