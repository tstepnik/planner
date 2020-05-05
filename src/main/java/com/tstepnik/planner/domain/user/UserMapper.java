package com.tstepnik.planner.domain.user;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    User toUser(UserDTO userDto);

    List<UserDTO> toDto(List<User> users);

    List<User> toUser(List<UserDTO> usersDto);
}

