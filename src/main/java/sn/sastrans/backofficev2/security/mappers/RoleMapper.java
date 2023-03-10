package sn.sastrans.backofficev2.security.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.security.dto.RoleDto;
import sn.sastrans.backofficev2.security.dto.RoleDto;
import sn.sastrans.backofficev2.security.models.Role;

import java.util.List;


@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDto, Role> {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);


    RoleDto toDto(Role Role);

    @InheritInverseConfiguration
    Role toEntity(RoleDto RoleDto);

    List<RoleDto> toDto(List<Role> Roles);
    List<Role> toEntity(List<RoleDto> RoleDtos);


//    default Role fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        Role Role = new Role();
//        Role.setId(id);
//        return  Role;
//    }
}
