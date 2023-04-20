package sn.sastrans.backofficev2.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.sastrans.backofficev2.security.models.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    private Integer id;
    @NotBlank
    private String firstname;

    @NotNull
    private String lastname;
    @NotBlank
    private String username;

    Set<RoleDto> roles = new HashSet<RoleDto>();
}