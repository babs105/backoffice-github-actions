package sn.sastrans.backofficev2.security.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {
    private Integer id;
    private String name;
    private String description;
}
