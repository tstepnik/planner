package com.tstepnik.planner.domain.user.userregistrationdto;

import com.tstepnik.planner.domain.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {

    User toUser(UserRegisterDTO userDto);
    List<UserRegisterDTO> toUser(List<UserRegisterDTO> usersDto);

}
