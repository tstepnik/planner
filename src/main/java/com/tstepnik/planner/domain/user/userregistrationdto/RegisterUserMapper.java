package com.tstepnik.planner.domain.user.userregistrationdto;

import com.tstepnik.planner.domain.user.User;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {

    RegisterUserDto toDto(User user);
    List<RegisterUserDto> toDto(List<User> users);

    User toUser(RegisterUserDto userDto);
    List<RegisterUserDto> toUser(List<RegisterUserDto> usersDto);

}
