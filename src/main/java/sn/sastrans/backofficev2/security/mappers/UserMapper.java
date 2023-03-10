package sn.sastrans.backofficev2.security.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.security.dto.UserDto;
import sn.sastrans.backofficev2.security.models.User;


import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserDto toDto(User user);

    @InheritInverseConfiguration
    User toEntity(UserDto UserDto);

    List<UserDto> toDto(List<User> users);
    List<User> toEntity(List<UserDto> userDtos);


//    default User fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        User User = new User();
//        User.setId(id);
//        return  User;
//    }
}
