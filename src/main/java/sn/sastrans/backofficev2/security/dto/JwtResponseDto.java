package sn.sastrans.backofficev2.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {


    private String token;
    private String type ;
    private String firstName;
    private String lastName;
    private String username;

    private List<String> roles;

//    public JwtResponseDto(String accessToken, String firstName, String lastName, String username, List<String> roles) {
//        this.token = accessToken;
//        this.firstName=firstName;
//        this.lastName=lastName;
//        this.username = username;
//        this.roles = roles;
//    }



}