package sn.sastrans.backofficev2.security.dto;

import java.util.List;
public class JwtResponseDto {


    private String token;
    private String type = "Bearer";
    private String firstName;
    private String lastName;
    private String username;

    private List<String> roles;

    public JwtResponseDto(String accessToken, String firstName, String lastName, String username, List<String> roles) {
        this.token = accessToken;
        this.firstName=firstName;
        this.lastName=lastName;
        this.username = username;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }


}