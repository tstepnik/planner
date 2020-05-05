package com.tstepnik.planner.domain.user;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toUser(UserDto userDto);

    List<UserDto> toDto(List<User> users);

    List<User> toUser(List<UserDto> usersDto);
}

