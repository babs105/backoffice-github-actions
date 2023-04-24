package sn.sastrans.backofficev2.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SignupRequestDto {

    @NotBlank
    private String firstname;

    @NotNull
    private String lastname;
    @NotBlank
    @Size(max = 50)
    @Email
    private String username;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}
