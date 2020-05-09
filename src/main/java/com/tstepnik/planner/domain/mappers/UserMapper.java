package com.tstepnik.planner.domain.mappers;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.domain.user.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
}

